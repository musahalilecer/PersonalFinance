package com.musaecer.personalfinance.domain.usecase.paymentmethodusecase

import com.musaecer.personalfinance.data.remote.dto.AddPaymentMethodRequestDto
import com.musaecer.personalfinance.domain.model.PaymentMethod
import com.musaecer.personalfinance.domain.repository.PaymentMethodRepository
import com.musaecer.personalfinance.utils.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertPaymentMethodUseCase @Inject constructor(
    private val paymentMethodRepository: PaymentMethodRepository
) {
    suspend operator fun invoke(addPaymentMethodRequestDto: AddPaymentMethodRequestDto): Resource<Unit>{
         return try {
            require(addPaymentMethodRequestDto.name.isNotBlank()) { "Payment method name cannot be blank" }
        //    require(addPaymentMethodRequestDto.description.isNotBlank()) { "Payment method description cannot be blank" }
            val paymentMethod = PaymentMethod(
                name = addPaymentMethodRequestDto.name,
                description = addPaymentMethodRequestDto.description,
                type = addPaymentMethodRequestDto.type
            )
            paymentMethodRepository.insertPaymentMethod(paymentMethod)
                Resource.Success(Unit)
             // Success state
        }
        catch (e: Exception){
            Resource.Error(e.localizedMessage ?: "Unknown error", null)
            throw Exception("Failed to insert payment method: ${e.localizedMessage}")
        }
    }
}