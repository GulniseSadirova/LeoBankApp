package com.leobank.presentation.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leobank.domain.Edv

class SingleEdvViewModel: ViewModel() {
    private val _items = MutableLiveData<List<Edv>>()
    val items: LiveData<List<Edv>> = _items

    private val firestore = Firebase.firestore


    fun fetchProducts(itemId: Int) {
        Log.d("SingleItemViewModel", "fetchProducts - itemId: $itemId")

        firestore.collection("edv")
            .whereEqualTo("itemId", itemId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val productList = mutableListOf<Edv>()

                for (document in querySnapshot.documents) {

                    val product = Edv(
                        itemId = document.getLong("itemId")?.toInt() ?: 0,
                        ficsalİd = document.getString("ficsalİd") ?: "",
                        edv = document.getLong("edv")?.toFloat() ?: 0.0f,
                        mebleg = document.getLong("mebleg")?.toFloat() ?: 0.0f,
                        date=document.getString("date") ?: ""
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