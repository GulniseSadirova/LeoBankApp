package com.leobank.presentation.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransferViewModel : ViewModel() {

    private val _transferAmount = MutableLiveData<Double>()
    val transferAmount: LiveData<Double>
        get() = _transferAmount

    private val _cardNumber = MutableLiveData<String>()
    val cardNumber: LiveData<String>
        get() = _cardNumber

    private lateinit var sharedPreferences: SharedPreferences

    fun initSharedPreferences(context: Context) {
        sharedPreferences = context.getSharedPreferences("TransferPrefs", Context.MODE_PRIVATE)

        val lastTransferAmount = sharedPreferences.getFloat("lastTransferAmount", 0f).toDouble()
        val lastCardNumber = sharedPreferences.getString("lastCardNumber", "") ?: ""
        _transferAmount.value = lastTransferAmount
        _cardNumber.value = lastCardNumber
    }

    fun setTransferAmount(context: Context, amount: Double) {
        _transferAmount.value = amount

        sharedPreferences.edit().putFloat("lastTransferAmount", amount.toFloat()).apply()
    }

    fun updateTotalAmount(amount: Double) {
        val previousTotal = _transferAmount.value ?: 0.0

        val newTotal = previousTotal + amount
        _transferAmount.value = newTotal
        sharedPreferences.edit().putFloat("lastTransferAmount", newTotal.toFloat()).apply()
    }
}