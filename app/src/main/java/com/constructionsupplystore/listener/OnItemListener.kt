package com.constructionsupplystore.listener

import com.data.ProductData

interface OnItemListener {
    fun onChangeItemClick(position: Int, productData: ProductData)
    fun onDeleteItemClick(position: Int, productData: ProductData)

}