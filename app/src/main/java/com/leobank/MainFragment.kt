package com.leobank

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.leobank.databinding.FragmentMainBinding


class MainFragment : Fragment() {
  private lateinit var binding: FragmentMainBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMainBinding.inflate(inflater,container,false)
        click()
        balanceIncrease()
        sendCard()
        increaseAmountManually(0.0)
        payment()

        return binding.root
    }
    private fun click(){
        binding.myLeoCard.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_detailedCardFragment)
        }
    }
    private fun balanceIncrease(){
        binding.imageAdd.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_balanceIncreaseFragment)
        }
    }
    private fun sendCard(){
        binding.imageForward.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_sendToCardFragment)
        }
    }
    private fun payment(){
        binding.imageWallet.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_paymentFragment)
        }
    }
    private fun updateFirebase(amount: Double) {
        val db = Firebase.firestore
        val userUid = Firebase.auth.currentUser?.uid
        if (userUid != null) {
            db.collection("amount")
                .document(userUid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val currentBalance = document.getDouble("amount") ?: 0.0
                        val newBalance = currentBalance + amount
                        val accountData = hashMapOf(
                            "amount" to newBalance
                        )
                        db.collection("amount")
                            .document(userUid)
                            .set(accountData, SetOptions.merge())
                            .addOnSuccessListener {
                                Log.d(TAG, "Balance updated successfully!")
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error updating balance", e)
                            }
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->

                    Log.w(TAG, "Error getting documents: ", exception)
                }
        }
    }
    private fun increaseAmountManually(amount: Double) {
        val currentAmountStr = binding.txtMebleg.text.toString()
        val currentAmount = if (currentAmountStr.isNotEmpty()) {
            currentAmountStr.toDouble()
        } else {
            0.0
        }
        val newAmount = currentAmount + amount
        binding.txtMebleg.text = newAmount.toString()
        updateFirebase(amount)
        Log.d(TAG, "Firebase Balance Updated: $newAmount")

    }







}