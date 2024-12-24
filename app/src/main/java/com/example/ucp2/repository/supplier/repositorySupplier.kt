package com.example.ucp2.repository.supplier

import com.example.ucp2.data.entity.Supplier
import kotlinx.coroutines.flow.Flow

interface repositorySupplier {
    suspend fun insertSupplier(supplier: Supplier)
    fun getAllSupplier(): Flow<List<Supplier>>
    fun getSupplier(id: String): Flow<Supplier>
}