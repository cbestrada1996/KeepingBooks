package com.cestrada.keepingbooks.repository

import com.cestrada.keepingbooks.cache.PaymentCacheMapper
import com.cestrada.keepingbooks.cache.payment.PaymentCacheEntity
import com.cestrada.keepingbooks.cache.payment.PaymentDao
import com.cestrada.keepingbooks.model.Payment
import com.cestrada.keepingbooks.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay
import java.lang.Exception

class PaymentRepository constructor(
        private val paymentDao: PaymentDao,
        private val paymentCacheMapper: PaymentCacheMapper
) {

    suspend fun getPayments(): Flow<DataState<List<Payment>>> = flow {
        emit(DataState.Loading)
        try {
            val cachePayment = paymentDao.get()
            emit(DataState.Success(paymentCacheMapper.mapFromEntityList(cachePayment)))
        }catch (e:Exception){
            emit(DataState.Error(e))
        }
    }

}