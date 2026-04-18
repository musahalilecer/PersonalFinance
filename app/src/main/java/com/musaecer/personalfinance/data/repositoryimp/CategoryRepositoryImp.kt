package com.musaecer.personalfinance.data.repositoryimp

import com.musaecer.personalfinance.data.local.entity.CategoryEntity
import com.musaecer.personalfinance.data.mapper.CategoryMapper
import com.musaecer.personalfinance.data.remote.dao.CategoryDao
import com.musaecer.personalfinance.domain.model.Category
import com.musaecer.personalfinance.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImp @Inject constructor(
    private val categoryDao: CategoryDao
): CategoryRepository {

    override suspend fun getAllCategories(): List<Category> {
        var categories = categoryDao.getAllCategories()
        return categories.map { c ->
            CategoryMapper.toDomain(c)
        }
    }

    override suspend fun insertCategory(category: Category) {
        val entity = CategoryMapper.toEntity(category)
        return categoryDao.insertCategory(entity)
    }

    override suspend fun deleteCategory(categoryId: Int) {
        val category = categoryDao.getCategoryById(categoryId)?.id ?: return
        categoryDao.deleteCategoryById(category)
    }

    override suspend fun getCategoryById(id: Int): Category? {
        val categoryById = categoryDao.getCategoryById(id) ?: return null
        return CategoryMapper.toDomain(categoryById)
    }
}