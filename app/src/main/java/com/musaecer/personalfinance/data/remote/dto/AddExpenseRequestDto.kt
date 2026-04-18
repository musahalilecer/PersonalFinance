package com.musaecer.personalfinance.data.remote.dto

data class AddExpenseRequestDto(
    val amount: Double,
    val description: String,
    val date: String,
    val paymentMethodId: Int,
    val categoryId: Int
)
