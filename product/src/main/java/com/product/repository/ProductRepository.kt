package com.product.repository

import android.util.Log
import com.product.data.ProductData
import com.google.firebase.firestore.FirebaseFirestore
import com.product.ResultCallBack
import com.product.utils.Constants.CODE
import com.product.utils.Constants.COLLECTION_PATH
import com.product.utils.Constants.COUNT
import com.product.utils.Constants.FIRMA
import com.product.utils.Constants.INITIAL_PRICE
import com.product.utils.Constants.NAME
import com.product.utils.Constants.PRICE


interface ProductRepository {
    fun getProduct(resultCallBack: ResultCallBack<ProductData>)
    fun addProduct(productData: ProductData)
    fun deleteData(productData: ProductData)
    fun changeData(productData: ProductData, fieldValue: String)
    fun getDocumentId(
        collectionPath: String = COLLECTION_PATH,
        fieldValue: Any,
        fieldName: String = NAME,
        callback: (String?) -> Unit
    )
}

class ProductRepositoryImpl : ProductRepository {

    private val db = FirebaseFirestore.getInstance()



    override fun getProduct(resultCallBack: ResultCallBack<ProductData>) {
        db.collection(COLLECTION_PATH).get()
            .addOnSuccessListener { result ->
                val productList = result.documents.mapNotNull { it.toObject(ProductData::class.java) }
                resultCallBack.onSuccess(productList)
            }
            .addOnFailureListener { exception ->
                resultCallBack.onError(exception)
            }
    }

    override fun addProduct(productData: ProductData) {
        db.collection(COLLECTION_PATH).add(createHashMap(productData))
    }

    override fun deleteData(productData: ProductData) {
        getDocumentId(fieldValue = productData.name.toString()) { documentId ->
            val docRef = documentId?.let { db.collection(COLLECTION_PATH).document(it) }
            docRef?.delete()
        }
    }

    override fun changeData(productData: ProductData, fieldValue: String) {
        getDocumentId(fieldValue = fieldValue) { documentId ->
            val docRef = documentId?.let { db.collection(COLLECTION_PATH).document(it) }
            docRef?.update(createHashMap(productData))
        }
    }


    override fun getDocumentId(
        collectionPath: String,
        fieldValue: Any,
        fieldName: String,
        callback: (String?) -> Unit
    ) {
        db.collection(collectionPath)
            .whereEqualTo(fieldName, fieldValue)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    callback(null)
                } else {
                    val document = documents.first()
                    callback(document.id)
                }
            }.addOnFailureListener { exception ->
                Log.e("Error document ID:",exception.toString())
                callback(null)
            }
    }


    private fun createHashMap(productData: ProductData): Map<String, String?> {
        return hashMapOf(
            NAME to productData.name,
            FIRMA to productData.firma,
            CODE to productData.code,
            COUNT to productData.count,
            INITIAL_PRICE to productData.initialPrice,
            PRICE to productData.price,
        )
    }
}