package com.musaecer.personalfinance.data.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.musaecer.personalfinance.data.remote.dao.CategoryDao
import com.musaecer.personalfinance.data.remote.dao.ExpenseDao
import com.musaecer.personalfinance.data.local.database.AppDatabases
import com.musaecer.personalfinance.data.remote.dao.PaymentMethodDao
import com.musaecer.personalfinance.data.repositoryimp.CategoryRepositoryImp
import com.musaecer.personalfinance.data.repositoryimp.ExpenseRepositoryImp
import com.musaecer.personalfinance.data.repositoryimp.PaymentMethodRepositoryImp
import com.musaecer.personalfinance.domain.repository.CategoryRepository
import com.musaecer.personalfinance.domain.repository.ExpenseRepository
import com.musaecer.personalfinance.domain.repository.PaymentMethodRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabases {
        return Room.databaseBuilder(
            context,
            AppDatabases::class.java,
            "personal_finance_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(database: AppDatabases) = database.categoryDao()

    @Provides
    @Singleton
    fun provideExpenseDao(database: AppDatabases) = database.expenseDao()

    @Provides
    @Singleton
    fun provideRevenueDao(database: AppDatabases) = database.revenueDao()

    @Provides
    @Singleton
    fun providePaymentMethodDao(database: AppDatabases) = database.paymentMethodDao()
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule{

    @Provides
    @Singleton
    fun provideCategoryRepository(dao: CategoryDao): CategoryRepository {
        return CategoryRepositoryImp(dao)
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(dao: ExpenseDao, categoryDao: CategoryDao, paymentMethodDao: PaymentMethodDao): ExpenseRepository {
        return ExpenseRepositoryImp(dao, categoryDao, paymentMethodDao)
    }

    @Provides
    @Singleton
    fun providePaymentMethodRepository( // İsmi düzelttik
        dao: PaymentMethodDao
    ): PaymentMethodRepository {
        return PaymentMethodRepositoryImp(dao) // parametre ismini direkt geçtik
    }
}