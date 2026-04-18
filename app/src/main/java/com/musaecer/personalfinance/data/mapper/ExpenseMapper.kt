package com.musaecer.personalfinance.data.mapper

import com.musaecer.personalfinance.data.local.entity.CategoryEntity
import com.musaecer.personalfinance.data.local.entity.ExpenseEntity
import com.musaecer.personalfinance.data.local.entity.PaymentMethodEntity
import com.musaecer.personalfinance.domain.model.Category
import com.musaecer.personalfinance.domain.model.Expense
import com.musaecer.personalfinance.domain.model.PaymentMethod

object ExpenseMapper {
    fun toDomain(
        entity: ExpenseEntity,
        category: Category,
        payment: PaymentMethod
    ): Expense {
        return Expense(
            id = entity.id,
            amount = entity.amount,
            description = entity.description,
            date = entity.date,
            category = category,
            paymentMethod = payment
        )
    }

    fun toEntity(domain: Expense): ExpenseEntity {
        return ExpenseEntity(
            id = domain.id,
            amount = domain.amount,
            description = domain.description,
            date = domain.date,
            categoryId = domain.category.id,
            paymentMethodId = domain.paymentMethod.id
        )
    }
}