package com.musaecer.personalfinance.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class PaymentMethod(
    val id: Int = 0,
    val name: String,
    val description: String,
    val type: String,
)
