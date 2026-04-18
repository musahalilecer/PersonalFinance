package com.musaecer.personalfinance.domain.usecase.categoryusecase

import com.musaecer.personalfinance.data.remote.dto.AddCategoryRequestDto
import com.musaecer.personalfinance.domain.model.Category
import com.musaecer.personalfinance.domain.repository.CategoryRepository
import com.musaecer.personalfinance.utils.Resource
import javax.inject.Inject

class InsertCategoryUseCase @Inject
constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(addCategoryRequestDto: AddCategoryRequestDto) : Resource<Unit> {
        return try {
            val category = Category(
                category = addCategoryRequestDto.category
            )
            categoryRepository.insertCategory(category)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown error", null)
        }
    }
}