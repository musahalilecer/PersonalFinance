package com.musaecer.personalfinance.presentation.state

import java.time.LocalDate

data class Date(
    val date: LocalDate?, // null ise boş kutu
    val isCurrentMonth: Boolean,
    val isSelected: Boolean = false,
    val isToday: Boolean = false
)
