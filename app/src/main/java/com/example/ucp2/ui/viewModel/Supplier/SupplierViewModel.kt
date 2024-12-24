package com.example.ucp2.ui.viewModel.Supplier

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ucp2.data.entity.Supplier
import com.example.ucp2.repository.supplier.repositorySupplier

class SupplierViewModel(private val repositorySpl: repositorySupplier) : ViewModel() {
    var uiSupplierState by mutableStateOf(SupplierUIState())

    fun updateSupplierState(supplierEvent: SupplierEvent) {
        uiSupplierState = uiSupplierState.copy(supplierEvent = supplierEvent)
    }

    private fun validateSplFields(): Boolean {
        val event = uiSupplierState.supplierEvent
        val errorSplState = FormErrorSupplierState(
            nama = if (event.nama.isNotEmpty()) null else "Nama Tidak Boleh Kosong",
            kontak = if (event.kontak.isNotEmpty()) null else "Kontak Tidak Boleh Kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat Tidak Boleh Kosong",
        )

        uiSupplierState = uiSupplierState.copy(isEntrySplValid = errorSplState)
        return errorSplState.isSplValid()
    }

    suspend fun saveDataSupplier():Boolean {
        val currentSplEvent = uiSupplierState.supplierEvent

        return if (validateSplFields()) {
            try {
                repositorySpl.insertSupplier(currentSplEvent.toSupplierEntity())
                uiSupplierState = uiSupplierState.copy(
                    snackBarMessage = "Data Berhasil Disimpan",
                    supplierEvent = SupplierEvent(),
                    isEntrySplValid = FormErrorSupplierState()
                )
                true
            } catch (e: Exception) {
                uiSupplierState = uiSupplierState.copy(snackBarMessage = "Data Supplier Gagal Disimpan")
                false
            }
        } else {
            uiSupplierState = uiSupplierState.copy(snackBarMessage = "Input tidak valid. Periksa Data Kembali")
            false
        }
    }
    fun resetSnackbarMessage() {
        uiSupplierState = uiSupplierState.copy(snackBarMessage = null)
    }
}

data class SupplierUIState(
    val supplierEvent: SupplierEvent = SupplierEvent(),
    val isEntrySplValid: FormErrorSupplierState = FormErrorSupplierState(),
    val snackBarMessage: String? = null
)

data class FormErrorSupplierState(
    val nama: String? = null,
    val kontak: String? = null,
    val alamat: String? = null
) {
    fun isSplValid(): Boolean {
        return nama == null &&
                kontak == null && alamat == null
    }
}

fun SupplierEvent.toSupplierEntity(): Supplier = Supplier(
    id = id.toString(),
    nama = nama,
    kontak = kontak,
    alamat = alamat
)

data class SupplierEvent(
    val id: Int = 0,
    val nama: String = "",
    val kontak: String = "",
    val alamat: String = ""
)