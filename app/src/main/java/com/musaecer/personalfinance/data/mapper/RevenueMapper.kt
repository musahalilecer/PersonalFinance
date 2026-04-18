package com.musaecer.personalfinance.data.mapper

import com.musaecer.personalfinance.data.local.entity.RevenueEntity
import com.musaecer.personalfinance.domain.model.Revenue

object RevenueMapper {

    fun toDomain(
        entity: RevenueEntity,
    ): Revenue {
        return Revenue(
            id = entity.id,
            amount = entity.amount,
            description = entity.description,
            date = entity.date,
        )
    }

    fun toEntity(domain: Revenue): RevenueEntity {
        return RevenueEntity(
            id = domain.id,
            amount = domain.amount,
            description = domain.description,
            date = domain.date,
        )
    }
}