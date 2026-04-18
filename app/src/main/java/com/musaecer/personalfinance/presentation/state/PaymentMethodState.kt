package com.musaecer.personalfinance.presentation.state

import com.musaecer.personalfinance.domain.model.PaymentMethod

data class PaymentMethodState (
    val isLoading: Boolean = false,
    val paymentMethods: List<PaymentMethod> = emptyList(),
    val error: String? = null
)