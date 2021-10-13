package com.cestrada.keepingbooks.cache.payment

import androidx.room.*

@Dao
interface PaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(paymentCacheEntity: PaymentCacheEntity): Long

    @Query("SELECT * FROM payments ORDER BY date DESC")
    suspend fun get(): List<PaymentCacheEntity>

    @Delete
    suspend fun delete(paymentCacheEntity: PaymentCacheEntity): Int

}