package com.musaecer.personalfinance.data.mapper

import com.musaecer.personalfinance.data.local.entity.CategoryEntity
import com.musaecer.personalfinance.data.local.entity.ExpenseEntity
import com.musaecer.personalfinance.domain.model.Category
import com.musaecer.personalfinance.domain.model.Expense
import com.musaecer.personalfinance.domain.model.PaymentMethod

object CategoryMapper {
    fun toDomain(entity: CategoryEntity): Category =
        Category(
            id = entity.id,
            category = entity.category
            // senin alanların neyse ekle
        )

    fun toEntity(domain: Category): CategoryEntity =
        CategoryEntity(
            id = domain.id,
            category = domain.category
        )
}