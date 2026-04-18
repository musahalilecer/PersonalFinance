package com.musaecer.personalfinance.domain.usecase.expenseusecase

import com.musaecer.personalfinance.data.remote.dto.AddExpenseRequestDto
import com.musaecer.personalfinance.domain.model.Expense
import com.musaecer.personalfinance.domain.repository.CategoryRepository
import com.musaecer.personalfinance.domain.repository.ExpenseRepository
import com.musaecer.personalfinance.domain.repository.PaymentMethodRepository
import com.musaecer.personalfinance.utils.Resource
import javax.inject.Inject

class InsertExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val categoryRepository: CategoryRepository,
    private val paymentMethodRepository: PaymentMethodRepository
) {
    suspend operator fun invoke(expenseRequestDto: AddExpenseRequestDto): Resource<Unit> {
        return try {
            require(expenseRequestDto.categoryId > 0) { "CategoryId is can not be 0" }
            require(expenseRequestDto.paymentMethodId > 0) { "PaymentMethodId is can not be 0" }
            require(expenseRequestDto.amount > 0.0) { "Amount must be greater than 0" }

            val existCategory = categoryRepository.getCategoryById(expenseRequestDto.categoryId)
            if (existCategory == null) {
                throw Exception("Category not found")
            }

            val existPaymentMethod = paymentMethodRepository.getPaymentMethodById(expenseRequestDto.paymentMethodId)
            if (existPaymentMethod == null) {
                throw Exception("Payment method not found")
            }
            val expense = Expense(
                amount = expenseRequestDto.amount,
                description = expenseRequestDto.description,
                date = expenseRequestDto.date,
                category = existCategory,
                paymentMethod = existPaymentMethod
            )
            expenseRepository.insertExpense(expense)
            Resource.Success(Unit)
        }

        catch (e: Exception){
            Resource.Error(e.localizedMessage ?: "Unknown error", null)
        }
    }
}

/*
suspend operator fun invoke(expense: Expense): Resource<Unit> {
        return try {
            // 1) Business validation
            if (expense.amount <= 0.0) {
                return Resource.Error("Amount must be greater than 0")
            }
            if (expense.category.id <= 0) {
                return Resource.Error("Category must be selected")
            }
            if (expense.paymentMethod.id <= 0) {
                return Resource.Error("Payment method must be selected")
            }

            // 2) Reference validation (opsiyonel ama sağlam)
            val categoryExists = categoryRepository
                .getAllCategories()
                .any { it.id == expense.category.id }

            if (!categoryExists) {
                return Resource.Error("Selected category not found")
            }

            val paymentExists = paymentMethodRepository
                .fetchPaymentMethods()
                .any { it.id == expense.paymentMethod.id }

            if (!paymentExists) {
                return Resource.Error("Selected payment method not found")
            }

            // 3) Insert
            expenseRepository.insertExpense(expense.toEntity())
            Resource.Success(Unit)

        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}
 */