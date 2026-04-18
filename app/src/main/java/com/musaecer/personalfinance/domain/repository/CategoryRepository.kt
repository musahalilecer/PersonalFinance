package com.musaecer.personalfinance.domain.repository

import com.musaecer.personalfinance.data.local.entity.CategoryEntity
import com.musaecer.personalfinance.domain.model.Category

interface CategoryRepository {

    suspend fun getAllCategories(): List<Category>
    suspend fun insertCategory(category: Category)
    suspend fun deleteCategory(category: Int)
    suspend fun getCategoryById(id: Int): Category?
}