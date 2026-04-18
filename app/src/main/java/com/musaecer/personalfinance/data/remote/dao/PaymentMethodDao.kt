package com.musaecer.personalfinance.data.remote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.musaecer.personalfinance.data.local.entity.PaymentMethodEntity
import com.musaecer.personalfinance.domain.model.PaymentMethod

@Dao
interface PaymentMethodDao {
    @Query("SELECT * FROM payment_methods")
    suspend fun getAllPayments(): List<PaymentMethodEntity>

    @Query("SELECT * FROM payment_methods WHERE id = :id")
    suspend fun getPaymentById(id: Int): PaymentMethodEntity?

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertPayment(paymentMethod: PaymentMethodEntity)

    @Query("DELETE FROM payment_methods WHERE id = :paymentMethodId")
    suspend fun deletePaymentById(paymentMethodId: Int)
}