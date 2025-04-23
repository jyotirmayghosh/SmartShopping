package com.jyotirmay.smartshopping.repository

import com.jyotirmay.smartshopping.data.local.ProductDao
import com.jyotirmay.smartshopping.data.local.ProductEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock

class RepositoryTest {

    @Mock
    var productDao: ProductDao = mock()

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `addToCart should call productDao insert`() = runTest {
        val product = ProductEntity(
            serialNo = "S12234",
            productName = "Dummy Item",
            productPrice = 300.00,
            image = "https://picsum.photos/id/250/400/400"
        )
        Mockito.`when`(productDao.insert(product)).thenReturn(Unit)

        val repo = Repository(productDao)
        repo.addToCart(product)
        verify(productDao).insert(product)
    }

    @Test
    fun `removeProduct should call productDao delete`() = runTest {
        val product = ProductEntity(
            serialNo = "S12234",
            productName = "Dummy Item",
            productPrice = 300.00,
            image = "https://picsum.photos/id/250/400/400"
        )
        Mockito.`when`(productDao.delete(product)).thenReturn(Unit)

        val repo = Repository(productDao)
        repo.removeProduct(product)
        verify(productDao).delete(product)
    }

    @Test
    fun `removeAllProducts should call productDao deleteAll`() = runTest {
        Mockito.`when`(productDao.deleteAll()).thenReturn(Unit)

        val repo = Repository(productDao)
        repo.removeAllProducts()
        verify(productDao).deleteAll()
    }

    @Test
    fun `getProducts should call productDao getProduct`() = runTest {
        val expectedProducts = listOf(
            ProductEntity(
                id = 1,
                serialNo = "SN001",
                productName = "Sample 1",
                productPrice = 100.00,
                image = "img1"
            ),
            ProductEntity(
                id = 2,
                serialNo = "SN002",
                productName = "Sample 2",
                productPrice = 200.00,
                image = "img2"
            )
        )
        Mockito.`when`(productDao.getProduct()).thenReturn(expectedProducts)

        val repo = Repository(productDao)
        val result = repo.getProducts().first()
        assertEquals(expectedProducts, result)
        verify(productDao).getProduct()
    }
}