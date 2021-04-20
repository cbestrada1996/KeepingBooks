package com.cestrada.keepingbooks.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cestrada.keepingbooks.cache.payment.PaymentCacheEntity
import com.cestrada.keepingbooks.cache.payment.PaymentDao

@Database(entities = [PaymentCacheEntity::class], version = 1)
abstract class PaymentsDatabase: RoomDatabase() {
    abstract fun paymentDao(): PaymentDao

    companion object {
        val DATABASE_NAME: String = "payments_db"
    }

}