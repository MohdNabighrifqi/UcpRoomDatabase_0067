package com.example.ucp2.ui.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.TokoApp
import com.example.ucp2.ui.viewModel.Barang.BarangHomeViewModel
import com.example.ucp2.ui.viewModel.Barang.BarangViewModel
import com.example.ucp2.ui.viewModel.Barang.DetailBarangViewModel
import com.example.ucp2.ui.viewModel.Barang.UpdateBarangViewModel
import com.example.ucp2.ui.viewModel.Supplier.SupplierHomeViewModel
import com.example.ucp2.ui.viewModel.Supplier.SupplierViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {

        initializer {
            SupplierViewModel(
                TokoApp().containerApp.repositorySupplier
            )
        }
        initializer {
            SupplierHomeViewModel(
                TokoApp().containerApp.repositorySupplier
            )
        }
        initializer {
            BarangViewModel(
                TokoApp().containerApp.repositoryBarang,
            )
        }
        initializer {
            BarangHomeViewModel(
                TokoApp().containerApp.repositoryBarang
            )
        }
        initializer {
            DetailBarangViewModel(
                createSavedStateHandle(),
                TokoApp().containerApp.repositoryBarang
            )
        }
        initializer {
            UpdateBarangViewModel(
                createSavedStateHandle(),
                TokoApp().containerApp.repositoryBarang,
            )
        }
    }
}

fun CreationExtras.TokoApp() : TokoApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TokoApp)