package com.cestrada.keepingbooks.cache.payment

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(paymentCacheEntity: PaymentCacheEntity): Long

    @Query("SELECT * FROM payments")
    suspend fun get(): List<PaymentCacheEntity>

}