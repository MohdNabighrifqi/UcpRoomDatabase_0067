package com.example.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.data.database.TokoDatabase
import com.example.ucp2.repository.barang.localRepositoryBarang
import com.example.ucp2.repository.barang.repositoryBarang
import com.example.ucp2.repository.supplier.localRepositorySupplier
import com.example.ucp2.repository.supplier.repositorySupplier

interface InterfaceContainerApp {
    val repositorySupplier : repositorySupplier
    val repositoryBarang : repositoryBarang
}

class ContainerApp (private val context : Context) :
    InterfaceContainerApp {
    override val repositoryBarang: repositoryBarang by lazy {
        localRepositoryBarang(TokoDatabase.getDatabase(context).barangDao())
    }
    override val repositorySupplier: repositorySupplier by lazy {
        localRepositorySupplier(TokoDatabase.getDatabase(context).supplierDao())
    }
}