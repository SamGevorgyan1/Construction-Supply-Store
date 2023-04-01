package com.product.data

import android.os.Parcel
import android.os.Parcelable

data class ProductData(
    val name:String?=null,
    val firma:String?=null,
    val code:String?=null,
    val count:String?=null,
    val initialPrice:String?=null,
    val price:String?=null,
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(firma)
        parcel.writeString(code)
        parcel.writeString(count)
        parcel.writeString(initialPrice)
        parcel.writeString(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductData> {
        override fun createFromParcel(parcel: Parcel): ProductData {
            return ProductData(parcel)
        }

        override fun newArray(size: Int): Array<ProductData?> {
            return arrayOfNulls(size)
        }
    }
}


