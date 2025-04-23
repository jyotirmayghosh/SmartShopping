package com.jyotirmay.smartshopping.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productEntity: ProductEntity)

    @Query("SELECT * FROM ProductEntity")
    suspend fun getProduct(): List<ProductEntity>

    @Delete
    suspend fun delete(productEntity: ProductEntity)

    @Query("DELETE FROM ProductEntity")
    suspend fun deleteAll()
}