package com.repository

import android.util.Log
import com.data.ProductData
import com.google.firebase.firestore.FirebaseFirestore
import com.ResultCallBack
import com.utils.Constants.CODE
import com.utils.Constants.COLLECTION_PATH
import com.utils.Constants.COUNT
import com.utils.Constants.FIRMA
import com.utils.Constants.INITIAL_PRICE
import com.utils.Constants.NAME
import com.utils.Constants.PRICE


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
                val productList =
                    result.documents.mapNotNull { it.toObject(ProductData::class.java) }
                resultCallBack.onSuccess(productList)
            }
            .addOnFailureListener { exception ->
                resultCallBack.onError(exception)
                Log.i("Error getting product",exception.toString())
            }
    }

    override fun addProduct(productData: ProductData) {

        db.collection(COLLECTION_PATH).add {
            (createHashMap(productData))
        }
        db.collection(COLLECTION_PATH).add(createHashMap(productData)).addOnSuccessListener {
            Log.i("${productData.name}","data saved")

        }.addOnFailureListener {
            Log.i("Error to add data",it.toString())
        }
    }

    override fun deleteData(productData: ProductData) {
        productData.code?.let { it ->
            getDocumentId(fieldValue = it, fieldName = CODE) { documentId ->
                val docRef = documentId?.let { db.collection(COLLECTION_PATH).document(it) }
                docRef?.delete()?.addOnSuccessListener{
                    Log.i("${productData.name}","data deleted")
                }?.addOnFailureListener {
                    Log.i("Error to delete data",it.toString())
                }
            }
        }
    }

    override fun changeData(productData: ProductData, fieldValue: String) {
        getDocumentId(fieldValue = fieldValue) { documentId ->
            val docRef = documentId?.let { db.collection(COLLECTION_PATH).document(it) }
            docRef?.update(createHashMap(productData))?.addOnSuccessListener {
                Log.i("${productData.name}","data changed")

            }?.addOnFailureListener {
                Log.i("Error to change data",it.toString())
            }
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
                Log.e("Error document ID:", exception.toString())
                callback(null)
            }
    }




    /**
        db.collection(COLLECTION_PATH).get()
            .addOnSuccessListener { result ->
                val productList =
                    result.documents.mapNotNull { it.toObject(ProductData::class.java) }
                return ResultCallBack2.Success(productList)
            }
            .addOnFailureListener { exception ->
                return ResultCallBack2.Error(exception)
            }
        **/
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
