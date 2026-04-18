package com.musaecer.personalfinance.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.musaecer.personalfinance.data.local.entity.CategoryEntity
import com.musaecer.personalfinance.data.local.entity.ExpenseEntity
import com.musaecer.personalfinance.data.local.entity.PaymentMethodEntity
import com.musaecer.personalfinance.data.local.entity.RevenueEntity
import com.musaecer.personalfinance.data.remote.dao.CategoryDao
import com.musaecer.personalfinance.data.remote.dao.ExpenseDao
import com.musaecer.personalfinance.data.remote.dao.PaymentMethodDao
import com.musaecer.personalfinance.data.remote.dao.RevenueDao

@Database(
    entities = [
        CategoryEntity::class,
        ExpenseEntity::class,
        PaymentMethodEntity::class,
        RevenueEntity::class
               ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabases: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun revenueDao(): RevenueDao
    abstract fun paymentMethodDao(): PaymentMethodDao
}