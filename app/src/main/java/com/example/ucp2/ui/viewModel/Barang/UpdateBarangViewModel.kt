package com.example.ucp2.ui.viewModel.Barang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Barang
import com.example.ucp2.repository.barang.repositoryBarang
import com.example.ucp2.ui.navigation.DestinasiUpdateBarang
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateBarangViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryBrg: repositoryBarang
) : ViewModel() {

    var updateBrgUiState by mutableStateOf(BarangUIState())
        private set

    private val _idBrg : String = checkNotNull(savedStateHandle[DestinasiUpdateBarang.idBrg])

    init {
        viewModelScope.launch {
            updateBrgUiState = repositoryBrg.getBarang(_idBrg)
                .filterNotNull()
                .first()
                .toUIStateBarang()
        }
    }

    fun UpdateStateBrg(barangEvent: BarangEvent) {
        updateBrgUiState = updateBrgUiState.copy(
            barangEvent = barangEvent
        )
    }

    fun validateBrgFields(): Boolean {
        val event = updateBrgUiState.barangEvent
        val errorBrgState = FormErrorBarangState(
            namaBarang = if (event.namaBarang.isNotEmpty()) null else "Nama Barang Tidak Boleh Kosong",
            deskripsi = if (event.deskripsi.isNotEmpty()) null else "Deskripsi Tidak Boleh Kosong",
            harga = if (event.harga.isNotEmpty()) null else "Harga Tidak Boleh Kosong",
            stok = if (event.stok.isNotEmpty()) null else "Stok Tidak Boleh Kosong",
            namaSupplier = if (event.namaSupplier.isNotEmpty()) null else "Nama Supplier Tidak Boleh Kosong"
        )

        updateBrgUiState = updateBrgUiState.copy(isEntryBarangValid = errorBrgState)
        return errorBrgState.isBarangValid()
    }

    suspend fun updateDataBrg(): Boolean {
        val currentBrgEvent = updateBrgUiState.barangEvent

        return if (validateBrgFields()) {
            try {
                repositoryBrg.updateBarang(currentBrgEvent.toBarangEntity())
                updateBrgUiState = updateBrgUiState.copy(
                    snackBarMessage = "Data Berhasil Diupdate",
                    barangEvent = BarangEvent(),
                    isEntryBarangValid = FormErrorBarangState()
                )
                true
            } catch (e: Exception) {
                updateBrgUiState = updateBrgUiState.copy(snackBarMessage = "Data Barang Gagal Diupdate")
                false
            }
        } else {
            updateBrgUiState = updateBrgUiState.copy(snackBarMessage = "Input tidak valid. Periksa Data Kembali")
            false
        }
    }
    fun resetSnackBarMessage() {
        updateBrgUiState = updateBrgUiState.copy(snackBarMessage = null)
    }
}

fun Barang.toUIStateBarang() : BarangUIState = BarangUIState(
    barangEvent = this.toDetailBaranglUiEvent()
)