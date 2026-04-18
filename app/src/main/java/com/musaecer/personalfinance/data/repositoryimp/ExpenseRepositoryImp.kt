package com.musaecer.personalfinance.data.repositoryimp

import com.musaecer.personalfinance.data.mapper.CategoryMapper
import com.musaecer.personalfinance.data.mapper.ExpenseMapper
import com.musaecer.personalfinance.data.mapper.PaymentMethodMapper
import com.musaecer.personalfinance.data.remote.dao.CategoryDao
import com.musaecer.personalfinance.data.remote.dao.ExpenseDao
import com.musaecer.personalfinance.data.remote.dao.PaymentMethodDao
import com.musaecer.personalfinance.domain.model.Expense
import com.musaecer.personalfinance.domain.repository.ExpenseRepository
import java.util.concurrent.Flow
import javax.inject.Inject

class ExpenseRepositoryImp @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val categoryDao: CategoryDao,
    private val paymentMethodDao: PaymentMethodDao
): ExpenseRepository {

    override suspend fun getAllExpenses(): List<Expense> {
        val entities = expenseDao.getAllExpenses()

        return entities.map { e ->
            val categoryEntity = categoryDao.getCategoryById(e.categoryId)
                ?: throw IllegalStateException("Category not found: ${e.categoryId}")

            val paymentEntity = paymentMethodDao.getPaymentById(e.paymentMethodId)
                ?: throw IllegalStateException("Payment method not found: ${e.paymentMethodId}")

            val category = CategoryMapper.toDomain(categoryEntity)
            val payment = PaymentMethodMapper.toDomain(paymentEntity)

            ExpenseMapper.toDomain(
                entity = e,
                category = category,
                payment = payment
            )
        }
    }

    override suspend fun insertExpense(expense: Expense) {
        val entity = ExpenseMapper.toEntity(expense)
        return expenseDao.insertExpense(entity)
    }

    override suspend fun deleteExpense(expenseId: Int) {
        val expense = expenseDao.getExpenseById(expenseId)?.id ?: return
        expenseDao.deleteExpenseById(expense)
    }

    override suspend fun getExpenseById(expenseId: Int): Expense? {
        val entity = expenseDao.getExpenseById(expenseId) ?: return null

        val categoryEntity = categoryDao.getCategoryById(entity.categoryId) ?: return null
        val paymentEntity = paymentMethodDao.getPaymentById(entity.paymentMethodId) ?: return null

        val category = CategoryMapper.toDomain(categoryEntity)
        val payment = PaymentMethodMapper.toDomain(paymentEntity)

        return ExpenseMapper.toDomain(
            entity = entity,
            category = category,
            payment = payment
        )
    }
}