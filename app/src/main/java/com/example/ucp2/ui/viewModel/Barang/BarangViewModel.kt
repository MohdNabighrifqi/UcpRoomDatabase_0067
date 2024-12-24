package com.example.ucp2.ui.viewModel.Barang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ucp2.data.entity.Barang
import com.example.ucp2.repository.barang.repositoryBarang

class BarangViewModel
    (private val repositoryBarang: repositoryBarang) : ViewModel() {
    var uiBarangState by mutableStateOf(BarangUIState())

    fun updateBarangState(barangEvent: BarangEvent) {
        uiBarangState = uiBarangState.copy(barangEvent = barangEvent)
    }

    private fun validateBrgFields(): Boolean {
        val event = uiBarangState.barangEvent
        val errorBarangState = FormErrorBarangState(
            namaBarang = if (event.namaBarang.isNotEmpty()) null else "Nama Barang Tidak Boleh Kosong",
            deskripsi = if (event.deskripsi.isNotEmpty()) null else "Deskripsi Tidak Boleh Kosong",
            harga = if (event.harga.isNotEmpty()) null else "Harga Tidak Boleh Kosong",
            stok = if (event.stok.isNotEmpty()) null else "Stok Tidak Boleh Kosong",
            namaSupplier = if (event.namaSupplier.isNotEmpty()) null else "Nama Supplier Tidak Boleh Kosong"
        )

        uiBarangState = uiBarangState.copy(isEntryBarangValid = errorBarangState)
        return errorBarangState.isBarangValid()
    }

    suspend fun saveDataBarang(): Boolean {
        val currentBrgEvent = uiBarangState.barangEvent

        return if (validateBrgFields()) {
            try {
                repositoryBarang.insertBarang(currentBrgEvent.toBarangEntity())
                uiBarangState = uiBarangState.copy(
                    snackBarMessage = "Data Berhasil Disimpan",
                    barangEvent = BarangEvent(),
                    isEntryBarangValid = FormErrorBarangState()
                )
                true
            } catch (e: Exception) {
                uiBarangState = uiBarangState.copy(snackBarMessage = "Data Barang Gagal Disimpan")
                false
            }
        } else {
            uiBarangState =
                uiBarangState.copy(snackBarMessage = "Input tidak valid. Periksa Data Kembali")
            false
        }
    }
    fun resetSnackbarMessage() {
        uiBarangState = uiBarangState.copy(snackBarMessage = null)
    }
}

data class BarangUIState(
    val barangEvent: BarangEvent = BarangEvent(),
    val isEntryBarangValid: FormErrorBarangState = FormErrorBarangState(),
    val snackBarMessage: String? = null
)

data class FormErrorBarangState(
    val id: String? = null,
    val namaBarang: String? = null,
    val deskripsi: String? = null,
    val harga: String? = null,
    val stok: String? = null,
    val namaSupplier: String? = null
) {
    fun isBarangValid(): Boolean {
        return id == null && namaBarang == null && harga == null &&
                deskripsi == null && stok == null && namaSupplier == null
    }
}

fun BarangEvent.toBarangEntity(): Barang = Barang(
    id = id,
    namaBarang = namaBarang,
    deskripsi = deskripsi,
    harga = harga.toIntOrNull() ?: 0,
    stok = stok.toIntOrNull() ?: 0,
    namaSupplier = namaSupplier
)

data class BarangEvent(
    val id: String = "",
    val namaBarang: String = "",
    val deskripsi: String = "",
    val harga: String = "",
    val stok: String = "",
    var namaSupplier: String = ""
)