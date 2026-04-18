package com.musaecer.personalfinance.domain.usecase.revenueusecase

import com.musaecer.personalfinance.domain.model.Revenue
import com.musaecer.personalfinance.domain.repository.RevenueRepository
import com.musaecer.personalfinance.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRevenueUseCase @Inject constructor(
    private val revenueRepository: RevenueRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Revenue>>> = flow {
        try {
            emit(Resource.Loading())
            val revenue = revenueRepository.getAllRevenues()
            emit(Resource.Success(revenue))

        }
        catch (e: Exception){
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}