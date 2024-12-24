package com.example.ucp2.ui.viewModel.Barang

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Barang
import com.example.ucp2.repository.barang.repositoryBarang
import com.example.ucp2.ui.navigation.DestinasiDetailBarang
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailBarangViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryBrg: repositoryBarang,
) : ViewModel() {
    private val _id : String = checkNotNull(savedStateHandle[DestinasiDetailBarang.idBrg])

    val detailBrgUiState: StateFlow<DetailBarangUiState> = repositoryBrg.getBarang(_id)
        .filterNotNull()
        .map {
            DetailBarangUiState(
                detailUiBrgEvent = it.toDetailBaranglUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailBarangUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(DetailBarangUiState(
                isLoading = false,
                isError = true,

                ))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailBarangUiState(
                isLoading = true
            )
        )

    fun deleteBrg(){
        detailBrgUiState.value.detailUiBrgEvent.toBarangEntity().let {
            viewModelScope.launch {
                repositoryBrg.deleteBarang(it)
            }
        }
    }
}

data class DetailBarangUiState(
    val detailUiBrgEvent: BarangEvent = BarangEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorBrgMessage: String = ""
) {
    val isUiBarangEmpty: Boolean
        get() = detailUiBrgEvent == BarangEvent()

    val isUiBarangEventNotEmpty:Boolean
        get() = detailUiBrgEvent != BarangEvent()
}

fun Barang.toDetailBaranglUiEvent () : BarangEvent {
    return BarangEvent(
        id = id,
        namaBarang = namaBarang,
        deskripsi = deskripsi,
        harga = harga.toString(),
        stok = stok.toString(),
        namaSupplier = namaSupplier
    )
}