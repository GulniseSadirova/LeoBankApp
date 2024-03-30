package com.leobank.presentation.viewmodel

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.leobank.domain.Capitals
import com.leobank.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class CapitalsFragmentViewModel @Inject constructor(val firestore: FirebaseFirestore): ViewModel() {

    private val _capitalList = MutableLiveData<List<Capitals>>()
    val capitalList: LiveData<List<Capitals>> get() = _capitalList

    fun getAllCapitals(context: Context) {
        fetchUsers()
    }

    private fun fetchUsers() {
        firestore.collection("capitals").get()
            .addOnSuccessListener { querySnapshot ->
                val capitalList = mutableListOf<Capitals>()
                for (document in querySnapshot.documents) {
                    val user = document.toObject(Capitals::class.java)
                    user?.let { capitalList.add(it) }
                }
                _capitalList.postValue(capitalList)
            }
            .addOnFailureListener { exception ->
                Log.e(ContentValues.TAG, "Error getting users", exception)
            }
    }
}