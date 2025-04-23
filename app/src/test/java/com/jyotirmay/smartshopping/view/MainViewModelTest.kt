package com.jyotirmay.smartshopping.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jyotirmay.smartshopping.data.local.ProductDao
import com.jyotirmay.smartshopping.data.local.ProductEntity
import com.jyotirmay.smartshopping.getOrAwaitValue
import com.jyotirmay.smartshopping.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private var repo: Repository = mock()
    private lateinit var viewModel: MainViewModel

    private val productObserver: Observer<List<ProductEntity>> = mock()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(repo)
    }


    @Test
    fun `addProduct should call repository addToCart`() = runTest {
        val serialNo = "SN123"
        val product = ProductEntity(
            serialNo = serialNo,
            productName = "Dummy Item",
            productPrice = 300.00,
            image = "https://picsum.photos/id/250/400/400"
        )

        Mockito.`when`(repo.addToCart(product)).thenReturn(Unit)

        viewModel.addProduct(serialNo)
        advanceUntilIdle()
        Mockito.verify(repo).addToCart(product)
    }

    @Test
    fun `getProducts should update productLiveData`() = runTest {
        val expectedList = listOf(
            ProductEntity(1, "SN001", "Watch", 200.00, "image1")
        )
        Mockito.`when`(repo.getProducts()).thenReturn(flowOf(expectedList))

        viewModel.getProducts()
        testDispatcher.scheduler.advanceUntilIdle()

        val actualList = viewModel.productLiveData.getOrAwaitValue()
        assertEquals(expectedList, actualList)
    }

    @Test
    fun `removeProduct should call repository removeProduct`() = runTest {
        val product = ProductEntity(1, "UUID1", "SN001", 200.00, "image")

        Mockito.`when`(repo.removeProduct(product)).thenReturn(Unit)
        viewModel.removeProduct(product)
        advanceUntilIdle()
        Mockito.verify(repo).removeProduct(product)
    }

    @Test
    fun `clearAllProducts should call repository removeAllProducts`() = runTest {
        viewModel.clearAllProducts()
        advanceUntilIdle()
        Mockito.verify(repo).removeAllProducts()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel.productLiveData.removeObserver(productObserver)
    }
}