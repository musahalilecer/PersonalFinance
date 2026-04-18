package com.musaecer.personalfinance.data.repositoryimp

import com.musaecer.personalfinance.data.local.entity.PaymentMethodEntity
import com.musaecer.personalfinance.data.mapper.PaymentMethodMapper
import com.musaecer.personalfinance.data.remote.dao.PaymentMethodDao
import com.musaecer.personalfinance.domain.model.PaymentMethod
import com.musaecer.personalfinance.domain.repository.PaymentMethodRepository
import javax.inject.Inject

class PaymentMethodRepositoryImp @Inject constructor(
    private val paymentMethodDao: PaymentMethodDao
): PaymentMethodRepository {

    override suspend fun fetchPaymentMethods(): List<PaymentMethod> {
        val methods = paymentMethodDao.getAllPayments()
        return methods.map {
            PaymentMethodMapper.toDomain(it)
        }
    }

    override suspend fun getPaymentMethodById(id: Int): PaymentMethod? {
        val method = paymentMethodDao.getPaymentById(id) ?: return null
        return PaymentMethodMapper.toDomain(method)
    }

    override suspend fun insertPaymentMethod(paymentMethod: PaymentMethod) {
        val entity = PaymentMethodMapper.toEntity(paymentMethod)
        paymentMethodDao.insertPayment(entity)
    }
}