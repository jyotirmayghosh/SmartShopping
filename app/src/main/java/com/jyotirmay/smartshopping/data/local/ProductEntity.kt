package com.jyotirmay.smartshopping.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val serialNo: String,
    val productName: String,
    val productPrice: Double,
    val image: String
)