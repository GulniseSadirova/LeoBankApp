package com.leobank.domain

data class Spending(
    val title: String = "",
    val explanation: String = "",
    val imageUrl:String="",
    val price: Double = 0.0,
    val itemId:Int=0,

)
