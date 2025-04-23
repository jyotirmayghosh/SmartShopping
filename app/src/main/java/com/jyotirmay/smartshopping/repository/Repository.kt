package com.jyotirmay.smartshopping.repository

import com.jyotirmay.smartshopping.data.local.ProductDao
import com.jyotirmay.smartshopping.data.local.ProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(private val productDao: ProductDao) {

    suspend fun addToCart(productEntity: ProductEntity) {
        productDao.insert(productEntity)
    }

    suspend fun getProducts(): Flow<List<ProductEntity>> = flow {
        val record = productDao.getProduct()
        emit(record)
    }

    suspend fun removeProduct(productEntity: ProductEntity) {
        productDao.delete(productEntity)
    }

    suspend fun removeAllProducts() {
        productDao.deleteAll()
    }

}