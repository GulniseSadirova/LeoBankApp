package com.leobank.presentation.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leobank.domain.Spending

class SingleItemViewModel : ViewModel() {
    private val _items = MutableLiveData<List<Spending>>()
    val items: LiveData<List<Spending>> = _items

    private val firestore = Firebase.firestore

    fun fetchProducts(itemId: Int) {
        Log.d("SingleItemViewModel", "fetchProducts - itemId: $itemId")

        firestore.collection("spendings")
            .whereEqualTo("itemId", itemId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val productList = mutableListOf<Spending>()
                for (document in querySnapshot.documents) {

                    val product = Spending(
                        itemId = document.getLong("itemId")?.toInt() ?: 0,
                        title = document.getString("title") ?: "",
                        explanation = document.getString("explanation") ?: "",
                        imageUrl = document.getString("imageUrl") ?: "",
                        price = document.getLong("price")?.toDouble() ?: 0.0
                    )
                    productList.add(product)
                }
                _items.postValue(productList)
                Log.d("SingleItemViewModel", "fetchProducts - productList size: ${productList.size}")
            }
            .addOnFailureListener { exception ->
                Log.e(ContentValues.TAG, "Error getting products", exception)
            }
    }

}
