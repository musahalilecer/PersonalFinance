package com.musaecer.personalfinance.presentation.state

import com.musaecer.personalfinance.domain.model.Expense

data class ExpenseState(
    val isLoading: Boolean = false,
    val error: String = "",
    val expense: Expense? = null,
    val expenses: List<Expense> = emptyList(),
    val isDeleteSuccess: Boolean? = null,
    val isInsertSuccess: Boolean? = null,

)
