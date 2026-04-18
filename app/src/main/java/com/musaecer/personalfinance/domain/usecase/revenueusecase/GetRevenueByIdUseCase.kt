package com.musaecer.personalfinance.domain.usecase.revenueusecase

import com.musaecer.personalfinance.domain.model.Revenue
import com.musaecer.personalfinance.domain.repository.RevenueRepository
import com.musaecer.personalfinance.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRevenueByIdUseCase @Inject constructor(
    private val revenueRepository: RevenueRepository
) {
    suspend operator fun invoke(revenueId: Int): Flow<Resource<Revenue>> = flow {
        try {
            emit(Resource.Loading())
            val revenue = revenueRepository.getRevenueById(revenueId)
            if (revenue != null) {
                emit(Resource.Success(revenue))
            } else {
                emit(Resource.Error("Revenue not found"))
            }
        }
        catch (e: Exception){
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}