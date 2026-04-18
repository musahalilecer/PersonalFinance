package com.musaecer.personalfinance.domain.usecase.revenueusecase

import com.musaecer.personalfinance.data.remote.dto.AddRevenueRequestDto
import com.musaecer.personalfinance.domain.model.Revenue
import com.musaecer.personalfinance.domain.repository.RevenueRepository
import com.musaecer.personalfinance.utils.Resource
import javax.inject.Inject

class InsertRevenueUseCase @Inject constructor(
    private val revenueRepository: RevenueRepository
) {
    suspend operator fun invoke(revenueDto: AddRevenueRequestDto): Resource<Unit>{
        return try {
            Resource.Loading(Unit)
            require(revenueDto.amount > 0.0) { "Amount must be greater than 0" }

            val revenue = Revenue(
                amount = revenueDto.amount,
                description = revenueDto.description,
                date = revenueDto.date
            )
            revenueRepository.insertRevenue(revenue)
            Resource.Success(Unit)
        }

        catch (e: Exception){
            Resource.Error(e.localizedMessage ?: "Unknown error", null)
        }
    }
}