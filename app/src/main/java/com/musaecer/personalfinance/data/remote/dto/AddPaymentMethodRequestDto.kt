package com.musaecer.personalfinance.data.remote.dto

data class AddPaymentMethodRequestDto (
    val name: String,
    val description: String,
    val type: String
)