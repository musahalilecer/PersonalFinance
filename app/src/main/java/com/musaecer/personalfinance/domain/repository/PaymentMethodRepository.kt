package com.musaecer.personalfinance.domain.repository

import com.musaecer.personalfinance.data.local.entity.PaymentMethodEntity
import com.musaecer.personalfinance.domain.model.PaymentMethod

interface PaymentMethodRepository {
    suspend fun fetchPaymentMethods(): List<PaymentMethod>
    suspend fun getPaymentMethodById(id: Int): PaymentMethod?
    suspend fun insertPaymentMethod(paymentMethod: PaymentMethod)
}