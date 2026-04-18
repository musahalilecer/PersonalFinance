package com.musaecer.personalfinance.domain.usecase.revenueusecase

import com.musaecer.personalfinance.domain.repository.RevenueRepository
import com.musaecer.personalfinance.utils.Resource
import javax.inject.Inject

class DeleteRevenueUseCase @Inject constructor(
    private val revenueRepository: RevenueRepository
) {
    operator suspend fun invoke(id: Int): Resource<Unit> {
        return try {
            Resource.Loading(Unit)
            revenueRepository.deleteRevenue(id)
            Resource.Success(Unit)
        }
        catch (e: Exception){
            Resource.Error(e.localizedMessage ?: "Unknown error", null)
        }
    }
}