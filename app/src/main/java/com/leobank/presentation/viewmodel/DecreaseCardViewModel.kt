package com.leobank.presentation.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DecreaseCardViewModel:ViewModel() {
    private val _currentAmount = MutableLiveData<Double>()
    val currentAmount: LiveData<Double>
        get() = _currentAmount

    init {
        // Default değer, eğer gerekliyse burada ayarlanabilir.
        _currentAmount.value = 0.0
    }

    fun updateCurrentAmount(amount: Double) {
        val currentValue = _currentAmount.value ?: 0.0
        _currentAmount.value = currentValue - amount

    }
}