package com.cestrada.keepingbooks.cache.payment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
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

@Entity(tableName = "tags", indices = [Index(value = ["description"], unique = true)])
data class TagEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int,
        @ColumnInfo(name = "description")
        var description: String
)

@Entity(tableName = "payments_tags", primaryKeys = ["payment_id", "tag_id"])
data class PaymentsTags(
        @ColumnInfo(name = "payment_id")
        var paymentId: Int,
        @ColumnInfo(name = "tag_id")
        var tagId: Int
)