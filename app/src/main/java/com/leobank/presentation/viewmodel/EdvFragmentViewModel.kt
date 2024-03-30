package com.leobank.presentation.viewmodel

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leobank.domain.Edv
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EdvFragmentViewModel @Inject constructor(val firestore: FirebaseFirestore ) :ViewModel() {

    private val _productList = MutableLiveData<List<Edv>>()
    val productList: LiveData<List<Edv>> get() = _productList
    fun getAllProducts(context: Context) {
        fetchProducts()
    }


    private fun fetchProducts() {
        firestore.collection("edv").get()
            .addOnSuccessListener { querySnapshot ->
                val productList = mutableListOf<Edv>()
                for (document in querySnapshot.documents) {
                    val product = document.toObject(Edv::class.java)
                    product?.let { productList.add(it) }
                }
                _productList.postValue(productList)

            }
            .addOnFailureListener { exception ->
                Log.e(ContentValues.TAG, "Error getting products", exception)
            }
    }

}