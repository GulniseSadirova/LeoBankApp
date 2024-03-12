package com.leobank

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainFragmentViewModel:ViewModel() {
    private val firestore = Firebase.firestore

    private val _productList = MutableLiveData<List<Spending>>()
    val productList: LiveData<List<Spending>> get() = _productList

    private val _totalAmount = MutableLiveData<Double>()
    val totalAmount: LiveData<Double>
        get() = _totalAmount


    fun getAllProducts(context: Context) {
       fetchProducts()
        }


    private fun fetchProducts() {
        firestore.collection("spendings").get()
            .addOnSuccessListener { querySnapshot ->
                val productList = mutableListOf<Spending>()
                for (document in querySnapshot.documents) {
                    val product = document.toObject(Spending::class.java)
                    product?.let { productList.add(it) }
                }
                _productList.postValue(productList)

            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting products", exception)
            }
    }

    fun addToTotalAmount(amount: Double) {
        val currentTotal = _totalAmount.value ?: 0.0
        _totalAmount.value = currentTotal + amount
        saveTotalAmountToFirestore(currentTotal + amount)
    }
    private fun saveTotalAmountToFirestore(total: Double) {

        val userId = "8Xj96yvMMsUuOweN5gtUky54qbH2"

        val docRef = firestore.collection("amount").document(userId)

        docRef.update("totalAmount", total)
            .addOnSuccessListener {
                Log.d(TAG, "Total amount updated successfully")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error updating total amount", e)
            }
    }

}