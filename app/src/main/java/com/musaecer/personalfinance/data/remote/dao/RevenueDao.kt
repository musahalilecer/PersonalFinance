package com.musaecer.personalfinance.data.remote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.musaecer.personalfinance.data.local.entity.RevenueEntity
import com.musaecer.personalfinance.domain.model.Revenue

@Dao
interface RevenueDao {

    @Query("SELECT * FROM revenues")
    suspend fun getAllRevenues(): List<RevenueEntity>

    @Query("SELECT * FROM revenues WHERE id = :id")
    suspend fun getRevenueById(id: Int): RevenueEntity?

    @Query("DELETE FROM revenues WHERE id = :id")
    suspend fun deleteRevenue(id: Int)

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertRevenue(revenue: RevenueEntity)
}