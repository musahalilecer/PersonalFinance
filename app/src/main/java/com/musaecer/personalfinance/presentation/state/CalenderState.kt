package com.musaecer.personalfinance.presentation.state

import java.time.LocalDate
import java.time.YearMonth

data class CalenderState(
    val yearMonth: YearMonth,
    val dates: List<Date>
)