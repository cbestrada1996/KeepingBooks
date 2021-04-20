package com.cestrada.keepingbooks.cache.payment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cestrada.keepingbooks.model.PaymentType
import com.cestrada.keepingbooks.util.SimpleStringDate

@Entity(tableName = "payments")
data class PaymentCacheEntity(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "id")
        var id: Int,
        @ColumnInfo(name = "description")
        var description: String,
        @ColumnInfo(name = "total")
        var total: Float,
        @ColumnInfo(name = "payment_type")
        var payment_type: String,
        @ColumnInfo(name = "date")
        var date: String
)