package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2.data.entity.Barang
import kotlinx.coroutines.flow.Flow

@Dao
interface BarangDao {
    @Insert
    suspend fun insertBarang(barang: Barang)

    @Update
    suspend fun updateBarang(barang: Barang)

    @Delete
    suspend fun deleteBarang(barang: Barang)

    @Query("SELECT * FROM Barang ORDER BY namaBarang")
    fun getAllBarang(): Flow<List<Barang>>

    @Query("SELECT * FROM Barang WHERE id = :id")
    fun getBarang(id: String): Flow<Barang>
}