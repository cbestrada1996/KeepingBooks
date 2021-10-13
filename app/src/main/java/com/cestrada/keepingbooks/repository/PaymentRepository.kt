package com.cestrada.keepingbooks.repository

import android.util.Log
import com.cestrada.keepingbooks.cache.PaymentCacheMapper
import com.cestrada.keepingbooks.cache.payment.PaymentCacheEntity
import com.cestrada.keepingbooks.cache.payment.PaymentDao
import com.cestrada.keepingbooks.model.Payment
import com.cestrada.keepingbooks.ui.payment.PaymentStateEvent
import com.cestrada.keepingbooks.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay
import java.lang.Exception

class PaymentRepository constructor(
        private val paymentDao: PaymentDao,
        private val paymentCacheMapper: PaymentCacheMapper
) {

    companion object {
        const val TAG = "PaymentRepository"
    }

    suspend fun getPayments(): Flow<DataState<List<Payment>, Type>> = flow {
        emit(DataState.Loading)
        try {
            val cachePayment = paymentDao.get()
            emit(DataState.Success(paymentCacheMapper.mapFromEntityList(cachePayment), Type.GET_ALL))
        }catch (e:Exception){
            emit(DataState.Error(e, Type.GET_ALL))
        }
    }

    suspend fun removePayment(payment: Payment): Flow<DataState<List<Payment>, Type>> = flow {
        emit(DataState.Loading)
        try {
            val affectedRows = paymentDao.delete(paymentCacheMapper.fromDomainModel(payment))
            if(affectedRows == 0) throw Exception("Record don't exist")
            Log.d(TAG, "removePayment: WORKS")
            val cachePayment = paymentDao.get()
            emit(DataState.Success(paymentCacheMapper.mapFromEntityList(cachePayment), Type.REMOVE))
        }catch (e:Exception){
            Log.d(TAG, "removePayment: ERROR")
            emit(DataState.Error(e, Type.REMOVE))
        }
    }

    enum class Type {
        GET_ALL, REMOVE, GET_ONE, UPDATED
    }

}