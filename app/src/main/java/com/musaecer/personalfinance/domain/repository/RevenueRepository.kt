package com.musaecer.personalfinance.domain.repository

import com.musaecer.personalfinance.data.local.entity.RevenueEntity
import com.musaecer.personalfinance.domain.model.Revenue

interface RevenueRepository {
    suspend fun getAllRevenues(): List<Revenue>
    suspend fun insertRevenue(revenue: Revenue)
    suspend fun deleteRevenue(revenueId: Int)
    suspend fun getRevenueById(revenueId: Int): Revenue?
}