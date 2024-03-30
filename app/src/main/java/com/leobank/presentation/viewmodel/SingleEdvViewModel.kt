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
import com.leobank.domain.Edv
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingleEdvViewModel @Inject constructor(val firestore: FirebaseFirestore) : ViewModel() {
    private val _items = MutableLiveData<List<Edv>>()
    val items: LiveData<List<Edv>> = _items



    fun fetchProducts(itemId: Int) {
        Log.d("SingleItemViewModel", "fetchProducts - itemId: $itemId")

        firestore.collection("edv")
            .whereEqualTo(ConstValue.ID_FIELD, itemId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val productList = mutableListOf<Edv>()

                for (document in querySnapshot.documents) {

                    val product = Edv(
                        itemId = document.getLong(ConstValue.ID_FIELD)?.toInt() ?: 0,
                        ficsalİd = document.getString(ConstValue.FICSALID_FIELD) ?: "",
                        edv = document.getLong(ConstValue.EDV_FIELD)?.toDouble() ?: 0.0,
                        mebleg = document.getLong(ConstValue.MEBLEG_FIELD)?.toDouble() ?: 0.0,
                        date =document.getString(ConstValue.DATE_FIELD) ?: ""
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