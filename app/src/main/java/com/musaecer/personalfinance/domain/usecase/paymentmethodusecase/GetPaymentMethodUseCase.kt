package com.musaecer.personalfinance.domain.usecase.paymentmethodusecase

import com.musaecer.personalfinance.domain.model.PaymentMethod
import com.musaecer.personalfinance.domain.repository.PaymentMethodRepository
import com.musaecer.personalfinance.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPaymentMethodUseCase @Inject constructor(
    private val paymentMethodRepository: PaymentMethodRepository
) {
    operator fun invoke(): Flow<Resource<List<PaymentMethod>>> = flow {
        emit(Resource.Loading())
        try {
            val paymentMethods = paymentMethodRepository.fetchPaymentMethods()
            emit(Resource.Success(paymentMethods))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}