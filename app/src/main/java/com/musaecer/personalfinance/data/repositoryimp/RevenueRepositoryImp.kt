package com.musaecer.personalfinance.data.repositoryimp

import com.musaecer.personalfinance.data.local.entity.RevenueEntity
import com.musaecer.personalfinance.data.mapper.RevenueMapper
import com.musaecer.personalfinance.data.remote.dao.RevenueDao
import com.musaecer.personalfinance.domain.model.Revenue
import com.musaecer.personalfinance.domain.repository.RevenueRepository
import javax.inject.Inject

class RevenueRepositoryImp @Inject constructor(
    private val revenueDao: RevenueDao,
): RevenueRepository {

    override suspend fun getAllRevenues(): List<Revenue> {
        val revenues = revenueDao.getAllRevenues()
        return revenues.map {
            RevenueMapper.toDomain(it)
        }
    }

    override suspend fun insertRevenue(revenue: Revenue) {
        val entity = RevenueMapper.toEntity(revenue)
        revenueDao.insertRevenue(entity)
    }

    override suspend fun deleteRevenue(revenueId: Int) {
        val revenue = revenueDao.getRevenueById(revenueId)?.id ?: return
        revenueDao.deleteRevenue(revenue)
    }

    override suspend fun getRevenueById(revenueId: Int): Revenue? {
        val revenueById = revenueDao.getRevenueById(revenueId) ?: return null
        return RevenueMapper.toDomain(revenueById)
    }
}