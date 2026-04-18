package com.musaecer.personalfinance.domain.usecase.expenseusecase

import com.musaecer.personalfinance.domain.repository.ExpenseRepository
import com.musaecer.personalfinance.utils.Resource
import javax.inject.Inject

class DeleteExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(expenseId: Int): Resource<Unit> {
        try{
            val expenseId = expenseRepository.getExpenseById(expenseId)?.id ?: return Resource.Error("Expense not found")
            if(expenseId <= 0){
                return Resource.Error("Invalid expense id")
            }
            else if(expenseId == null){
                return Resource.Error("Expense not found")
            }
            expenseRepository.deleteExpense(expenseId)
            return Resource.Success(Unit)
        }
        catch (e: Exception){
            return Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}