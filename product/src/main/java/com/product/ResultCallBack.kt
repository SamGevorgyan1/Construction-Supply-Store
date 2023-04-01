package com.product

import com.product.data.ProductData
import java.lang.Exception

interface ResultCallBack<T> {
    fun onSuccess(data: List<ProductData>)
    fun onError(msg: Exception)
}