package com.example.ucp2.ui.view.Supplier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.R
import com.example.ucp2.ui.viewModel.PenyediaViewModel
import com.example.ucp2.ui.viewModel.Supplier.FormErrorSupplierState
import com.example.ucp2.ui.viewModel.Supplier.SupplierEvent
import com.example.ucp2.ui.viewModel.Supplier.SupplierUIState
import com.example.ucp2.ui.viewModel.Supplier.SupplierViewModel
import com.example.ucp2.ui.widget.TopAppBar
import kotlinx.coroutines.launch

@Composable
fun InsertSplView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SupplierViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiSupplierState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackbarMessage()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Tambah Data Supplier",
                showBackButton = true,
                onBack = onBack
            )
        },
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {


            InsertBodySpl(
                uiState = uiState,
                onValueChange = { updatedEvent ->
                    viewModel.updateSupplierState(updatedEvent)
                },
                onClick = {
                    val isSaved = viewModel.saveDataSupplier()
                    if (isSaved) {
                        onNavigate()
                    }
                }
            )
        }
    }
}

@Composable
fun InsertBodySpl(
    modifier: Modifier = Modifier,
    onValueChange: (SupplierEvent) -> Unit,
    uiState: SupplierUIState,
    onClick: suspend () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Form Tambah Supplier",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.Start)
        )

        FormSupplier(
            supplierEvent = uiState.supplierEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntrySplValid,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                coroutineScope.launch {
                    onClick()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.primary)
            )
        ) {
            Text(
                text = "Simpan",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun FormSupplier(
    modifier: Modifier = Modifier,
    supplierEvent: SupplierEvent = SupplierEvent(),
    onValueChange: (SupplierEvent) -> Unit = {},
    errorState: FormErrorSupplierState = FormErrorSupplierState(),
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.medium
            ),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = supplierEvent.nama,
            onValueChange = { onValueChange(supplierEvent.copy(nama = it)) },
            label = { Text("Nama") },
            placeholder = { Text("Masukkan Nama Supplier") },
            leadingIcon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            isError = errorState.nama != null,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorBorderColor = Color.Red
            )
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 8.dp)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = supplierEvent.kontak,
            onValueChange = { onValueChange(supplierEvent.copy(kontak = it)) },
            label = { Text("Kontak") },
            placeholder = { Text("Masukkan Kontak") },
            leadingIcon = {
                Icon(
                    Icons.Filled.Phone,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            isError = errorState.kontak != null,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorBorderColor = Color.Red
            )
        )
        Text(
            text = errorState.kontak ?: "",
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 8.dp)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = supplierEvent.alamat,
            onValueChange = { onValueChange(supplierEvent.copy(alamat = it)) },
            label = { Text("Alamat") },
            placeholder = { Text("Masukkan Alamat") },
            leadingIcon = {
                Icon(
                    Icons.Filled.LocationOn,
                    contentDescription = null,
                )
            },
            isError = errorState.alamat != null,
            singleLine = false,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorBorderColor = Color.Red
            )
        )
        Text(
            text = errorState.alamat ?: "",
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}