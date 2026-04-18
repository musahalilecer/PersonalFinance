package com.musaecer.personalfinance.data.mapper

import com.musaecer.personalfinance.data.local.entity.PaymentMethodEntity
import com.musaecer.personalfinance.domain.model.PaymentMethod

object PaymentMethodMapper {

    fun toDomain(
        entity: PaymentMethodEntity,
    ): PaymentMethod {
        return PaymentMethod(
            id = entity.id,
            entity.name,
            entity.description,
            entity.type
        )
    }

    fun toEntity(domain: PaymentMethod): PaymentMethodEntity {
        return PaymentMethodEntity(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            type = domain.type
        )
    }
}