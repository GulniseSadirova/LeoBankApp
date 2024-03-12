package com.leobank

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

    fun setTransferAmount(amount: Double) {
        _transferAmount.value = amount
    }
    fun setCardNumber(cardNum: String) {
        _cardNumber.value = cardNum
    }
}