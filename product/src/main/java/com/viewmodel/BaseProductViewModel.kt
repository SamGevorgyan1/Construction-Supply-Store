package com.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.data.ProductData
import com.repository.ProductRepository
import com.ResultCallBack
import java.lang.Exception


abstract class BaseProductViewModel(private val productRepository: ProductRepository) :
    ViewModel() {

    private val _resultsLiveData: MutableLiveData<List<ProductData>?> = MutableLiveData()
    val resultsLiveData: LiveData<List<ProductData>?>
        get() = _resultsLiveData

    fun getProduct() {
        productRepository.getProduct(object : ResultCallBack<ProductData> {
            override fun onSuccess(data: List<ProductData>) {
                _resultsLiveData.value = data
            }

            override fun onError(msg: Exception) {
                Log.e("error", "error")
            }
        })
    }

    fun addProduct(productData: ProductData) = productRepository.addProduct(productData)

    fun deleteData(productData: ProductData) = productRepository.deleteData(productData)

    fun changeData(productData: ProductData, fieldValue: String) =
        productRepository.changeData(productData, fieldValue)

}