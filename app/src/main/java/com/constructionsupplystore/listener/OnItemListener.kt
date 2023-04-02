package com.constructionsupplystore.listener

import com.product.data.ProductData

interface OnItemListener {
    fun onChangeItemClick(position: Int, productData: ProductData)
    fun onDeleteItemClick(position: Int, productData: ProductData)

}