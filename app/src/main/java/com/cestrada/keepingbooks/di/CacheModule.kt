package com.cestrada.keepingbooks.di

import android.content.Context
import androidx.room.Room
import com.cestrada.keepingbooks.cache.PaymentsDatabase
import com.cestrada.keepingbooks.cache.payment.PaymentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun providePaymentsDB(@ApplicationContext context: Context): PaymentsDatabase {
        return Room.databaseBuilder(
                context,
                PaymentsDatabase::class.java,
                PaymentsDatabase.DATABASE_NAME
        )
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun providePaymentDao(paymentsDatabase: PaymentsDatabase): PaymentDao {
        return  paymentsDatabase.paymentDao()
    }

}