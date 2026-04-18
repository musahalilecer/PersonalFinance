package com.musaecer.personalfinance.domain.repository

import com.musaecer.personalfinance.data.local.entity.ExpenseEntity
import com.musaecer.personalfinance.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun getAllExpenses(): List<Expense>
    suspend fun insertExpense(expense: Expense)
    suspend fun deleteExpense(expenseId: Int)
    suspend fun getExpenseById(expenseId: Int): Expense?
}