package com.example.ucp2.ui.view.Barang

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.viewModel.Barang.UpdateBarangViewModel
import com.example.ucp2.ui.viewModel.PenyediaViewModel
import com.example.ucp2.ui.widget.TopAppBar

@Composable
fun UpdateBarangView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: UpdateBarangViewModel = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier.Companion
) {
    val uiState = viewModel.updateBrgUiState
    val snackbarHostState = remember { SnackbarHostState() }
    val title = "Edit Data Barang"

    LaunchedEffect (uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Long
            )
            viewModel.resetSnackBarMessage()
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                onBack = onBack,
                judul = title
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            InsertBodyBrg(
                uiState = uiState,
                formTitle = "Form $title",
                onValueChange = { updatedEvent ->
                    viewModel.UpdateStateBrg(updatedEvent)
                },
                onClick = {
                    val isSaved = viewModel.updateDataBrg()
                    if (isSaved) {
                        onNavigate()
                    }
                }
            )
        }
    }
}