package com.cestrada.keepingbooks.di

import com.cestrada.keepingbooks.cache.PaymentCacheMapper
import com.cestrada.keepingbooks.cache.payment.PaymentCacheEntity
import com.cestrada.keepingbooks.cache.payment.PaymentDao
import com.cestrada.keepingbooks.repository.PaymentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePaymentsRepository(
            paymentDao: PaymentDao,
            paymentCacheMapper: PaymentCacheMapper
    ): PaymentRepository {
        return PaymentRepository(paymentDao, paymentCacheMapper)
    }

}