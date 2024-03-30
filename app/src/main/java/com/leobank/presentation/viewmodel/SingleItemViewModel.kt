package com.leobank.presentation.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leobank.ConstValue
import com.leobank.domain.Spending
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingleItemViewModel @Inject constructor(val firestore: FirebaseFirestore) : ViewModel() {
    private val _items = MutableLiveData<List<Spending>>()
    val items: LiveData<List<Spending>> = _items


    fun fetchProducts(itemId: Int) {
        Log.d("SingleItemViewModel", "fetchProducts - itemId: $itemId")

        firestore.collection("spendings")
            .whereEqualTo(ConstValue.ID_FIELD, itemId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val productList = mutableListOf<Spending>()
                for (document in querySnapshot.documents) {

                    val product = Spending(
                        itemId = document.getLong(ConstValue.ID_FIELD)?.toInt() ?: 0,
                        title = document.getString(ConstValue.TITLE_FIELD) ?: "",
                        explanation = document.getString(ConstValue.EXPLANATION_FIELD) ?: "",
                        imageUrl = document.getString(ConstValue.IMAGEUrl_FIELD) ?: "",
                        price = document.getLong(ConstValue.PRICE_FIELD)?.toDouble() ?: 0.0
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
