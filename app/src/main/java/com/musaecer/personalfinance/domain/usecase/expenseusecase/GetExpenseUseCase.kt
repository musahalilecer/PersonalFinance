package com.musaecer.personalfinance.domain.usecase.expenseusecase

import com.musaecer.personalfinance.domain.model.Expense
import com.musaecer.personalfinance.domain.repository.ExpenseRepository
import com.musaecer.personalfinance.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository,
) {

    // GetExpenseUseCase
    operator fun invoke(): Flow<Resource<List<Expense>>> = flow {
        emit(Resource.Loading())

        // repositoryden gelen Flow'u dinlemeliyiz (collect)
        try {
            expenseRepository.getAllExpenses()
                .let { expenses ->
                    emit(Resource.Success(expenses))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

    /*
    fun invoke(): Flow<Resource<List<Expense>>> = flow {

        try {
            emit(Resource.Loading())
            val expenses = expenseRepository.getAllExpenses()
            emit(Resource.Success(expenses))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

     */
}

/*
        suspend operator fun invoke(): Flow<Resource<List<Expense>>> = flow {
        try {
            emit(Resource.Loading())

            val categories = categoryRepository.getAllCategories()
            val categoryMap: Map<Int, Category> = categories.filterNotNull().associateBy { it.id }

            val paymentMethods = paymentMethodRepository.fetchPaymentMethods()
            val paymentMethodMap: Map<Int, PaymentMethod> = paymentMethods.filterNotNull().associateBy { it.id }

            val expenseEntities = expenseRepository.getAllExpenses()

            val expenses = expenseEntities.mapNotNull { expenseEntity ->
                val category = categoryMap[expenseEntity.categoryId]
                    ?: categoryRepository.getCategoryById(expenseEntity.categoryId)

                val paymentMethod = paymentMethodMap[expenseEntity.paymentMethodId]
                    ?: paymentMethodRepository.getPaymentMethodById(expenseEntity.paymentMethodId)

                if (category == null || paymentMethod == null) return@mapNotNull null

                Expense(
                    id = expenseEntity.id,
                    amount = expenseEntity.amount,
                    date = expenseEntity.date,
                    description = expenseEntity.description,
                    category = category,
                    paymentMethod = paymentMethod
                )
            }

            emit(Resource.Success(expenses))
        } catch (e: Exception) {
            emit(Resource.Error("An error occurred: ${e.localizedMessage}"))
        }
    }
         */
/*
try {
    emit(Resource.Loading())
    val expenseEntities = expenseRepository.getAllExpenses()
    val expenses = expenseEntities.map { expenseEntity ->
        val paymentMethodEntity = paymentMethodRepository.getPaymentMethodById(expenseEntity.paymentMethodId)
        categories.find { it?.id == expenseEntity.categoryId }?.let {
            Category(
                id = it.id,
                category = it.category,
            )
        }?.let {
            paymentMethodEntity?.let {
                PaymentMethod(
                    id = it.id,
                    name = it.name,
                    type = it.type,
                    description = it.description
                )
            }?.let { it1 ->
                Expense(
                    id = expenseEntity.id,
                    amount = expenseEntity.amount,
                    date = expenseEntity.date,
                    description = expenseEntity.description,
                    category = it,
                    paymentMethod = it1
                )
            }
        }
    }
    emit(Resource.Success(expenses.filterNotNull()))
} catch (e: Exception) {
    emit(Resource.Error("An error occurred: ${e.localizedMessage}"))
}

 */