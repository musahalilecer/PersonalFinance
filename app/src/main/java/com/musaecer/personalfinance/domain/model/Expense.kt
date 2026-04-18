package com.musaecer.personalfinance.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Expense(
    val id: Int = 0,
    val amount: Double,
    val description: String,
    val date: String,
    val paymentMethod: PaymentMethod,
    val category: Category,
)
