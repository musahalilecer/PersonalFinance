package com.musaecer.personalfinance.domain.usecase.expenseusecase

import com.musaecer.personalfinance.domain.model.Expense
import com.musaecer.personalfinance.domain.repository.ExpenseRepository
import com.musaecer.personalfinance.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetExpenseByIdUserCase @Inject constructor(
    private val expenseRepository: ExpenseRepository,
) {
    fun invoke(id: Int): Flow<Resource<Expense>> = flow {
        try {
            emit(Resource.Loading())
            val expense = expenseRepository.getExpenseById(id)
            if (expense != null) {
                emit(Resource.Success(expense))
            } else {
                emit(Resource.Error("Expense not found"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}