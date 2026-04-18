package com.musaecer.personalfinance.data.remote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.musaecer.personalfinance.data.local.entity.ExpenseEntity
import com.musaecer.personalfinance.domain.model.Expense

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses")
    suspend fun getAllExpenses(): List<ExpenseEntity>

    @Query("SELECT * FROM expenses WHERE id = :id")
    suspend fun getExpenseById(id: Int): ExpenseEntity?

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("DELETE FROM expenses WHERE id = :expenseId")
    suspend fun deleteExpenseById(expenseId: Int)
}