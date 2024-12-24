package com.example.ucp2.ui.viewModel.Barang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Barang
import com.example.ucp2.repository.barang.repositoryBarang
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class BarangHomeViewModel(
    private val repositoryBrg: repositoryBarang
) : ViewModel() {

    val homeUiStateBrg: StateFlow<HomeUIStateBarang> = repositoryBrg.getAllBarang()
        .filterNotNull()
        .map {
            HomeUIStateBarang (
                listBarang = it.toList(),
                isLoading = false
            )
        }
        .onStart {
            emit(HomeUIStateBarang(isLoading = true))
            delay(900)
        }
        .catch {
            HomeUIStateBarang(
                isLoading = false,
                isError = true,
                errorMessage = it.message?: "Terjadi Kesalahan"
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUIStateBarang(
                isLoading = true
            )
        )
}

data class HomeUIStateBarang (
    val listBarang: List<Barang> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)