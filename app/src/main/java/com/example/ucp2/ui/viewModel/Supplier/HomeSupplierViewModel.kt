package com.example.ucp2.ui.viewModel.Supplier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Supplier
import com.example.ucp2.repository.supplier.repositorySupplier
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class SupplierHomeViewModel (
    private val repositorySupplier: repositorySupplier
) : ViewModel() {

    val homeUiStateSpl: StateFlow<HomeUIStateSupplier> = repositorySupplier.getAllSupplier()
        .filterNotNull()
        .map {
            HomeUIStateSupplier (
                listSpl = it.toList(),
                isLoading = false
            )
        }
        .onStart {
            emit(HomeUIStateSupplier(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUIStateSupplier(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUIStateSupplier(
                isLoading = true
            )
        )
}

data class HomeUIStateSupplier (
    val listSpl : List<Supplier> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)