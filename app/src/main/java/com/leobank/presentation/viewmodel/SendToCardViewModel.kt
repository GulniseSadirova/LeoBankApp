package com.leobank.presentation.viewmodel

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leobank.domain.Edv
import com.leobank.domain.User

class SendToCardViewModel: ViewModel() {
    private val firestore = Firebase.firestore

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> get() = _userList

    fun getAllUsers(context: Context) {
        fetchUsers()
    }

    private fun fetchUsers() {
        firestore.collection("users").get()
            .addOnSuccessListener { querySnapshot ->
                val userList = mutableListOf<User>()
                for (document in querySnapshot.documents) {
                    val user = document.toObject(User::class.java)
                    user?.let { userList.add(it) }
                }
                _userList.postValue(userList)
            }
            .addOnFailureListener { exception ->
                Log.e(ContentValues.TAG, "Error getting users", exception)
            }
    }
}
