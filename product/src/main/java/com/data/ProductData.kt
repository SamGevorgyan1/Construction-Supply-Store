package com.data


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductData(
    val name: String? = null,
    val firma: String? = null,
    val code: String? = null,
    val count: String? = null,
    val initialPrice: String? = null,
    val price: String? = null,
) : Parcelable



