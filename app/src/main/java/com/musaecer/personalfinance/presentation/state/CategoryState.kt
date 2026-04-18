package com.musaecer.personalfinance.presentation.state

import com.musaecer.personalfinance.domain.model.Category

data class CategoryState(
    val isLoading: Boolean = false,
    val error: String = "",
    val category: Category? = null,
    val categories: List<Category> = emptyList(),
    val isDeleteSuccess: Boolean? = null,
    val isInsertSuccess: Boolean? = null,
)