package com.jyotirmay.smartshopping.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyotirmay.smartshopping.data.local.ProductEntity
import com.jyotirmay.smartshopping.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: Repository) : ViewModel() {

    private val _productLiveData = MutableLiveData<List<ProductEntity>>()
    val productLiveData: LiveData<List<ProductEntity>> = _productLiveData

    fun addProduct(serialNo: String) {
        //Make api call to fetch product details
        val product = ProductEntity(
            serialNo = serialNo,
            productName = "Dummy Item",
            productPrice = 300.00,
            image = "https://picsum.photos/id/250/400/400"
        )
        viewModelScope.launch {
            repo.addToCart(product)
        }
    }

    fun getProducts() {
        viewModelScope.launch {
            val listFlow = repo.getProducts()
            listFlow.collect { response ->
                Log.d("test", response.toString())
                _productLiveData.postValue(response)
            }
        }
    }

    fun removeProduct(productEntity: ProductEntity) {
        viewModelScope.launch {
            repo.removeProduct(productEntity)
            getProducts()
        }
    }

    fun clearAllProducts() {
        viewModelScope.launch {
            repo.removeAllProducts()
        }
    }
}