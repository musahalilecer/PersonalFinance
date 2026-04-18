package com.musaecer.personalfinance.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.musaecer.personalfinance.domain.model.Category
import com.musaecer.personalfinance.domain.model.PaymentMethod

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class, // Kategori entity adın neyse (Örn: CategoryEntity)
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE // Kategori silinirse harcamalar da silinsin mi?
        )
    ]
)
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val description: String,
    val date: String,
    val paymentMethodId: Int,
    val categoryId: Int,
)