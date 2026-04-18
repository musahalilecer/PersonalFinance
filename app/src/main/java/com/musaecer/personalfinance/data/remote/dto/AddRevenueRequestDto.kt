package com.musaecer.personalfinance.data.remote.dto

data class AddRevenueRequestDto (
    val amount: Double,
    val description: String,
    val date: String,
)