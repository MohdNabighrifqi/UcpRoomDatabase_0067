package com.example.ucp2.repository.supplier

import com.example.ucp2.data.dao.SupplierDao
import com.example.ucp2.data.entity.Supplier
import kotlinx.coroutines.flow.Flow

data class localRepositorySupplier(
    private val supplierDao: SupplierDao
): repositorySupplier
{
    override suspend fun insertSupplier(supplier: Supplier) {
        supplierDao.insertSupplier(supplier)
    }

    override fun getAllSupplier(): Flow<List<Supplier>> =
        supplierDao.getAllSupplier()


    override fun getSupplier(id: String): Flow<Supplier> =
        supplierDao.getSupplier(id)

}



