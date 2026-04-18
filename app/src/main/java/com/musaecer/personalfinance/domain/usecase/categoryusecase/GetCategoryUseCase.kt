package com.musaecer.personalfinance.domain.usecase.categoryusecase

import com.musaecer.personalfinance.domain.model.Category
import com.musaecer.personalfinance.domain.repository.CategoryRepository
import com.musaecer.personalfinance.utils.Resource
import com.musaecer.personalfinance.utils.Resource.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
){
    operator fun invoke() : Flow<Resource<List<Category>>> = flow {
        try {
            emit(Resource.Loading())
            categoryRepository.getAllCategories().let { categories ->
                emit(
                    Success(
                        categories
                    )
                )
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}