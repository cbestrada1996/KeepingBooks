package com.cestrada.keepingbooks.model

import com.cestrada.keepingbooks.util.SimpleStringDate
import java.lang.Exception

data class Payment(
        var id: Int,
        var description: String,
        var total: Float,
        var payment_type: PaymentType,
        var date: SimpleStringDate
)

enum class PaymentType {
    CREDIT, DEBIT, CASH;
    companion object {
        @Throws(Exception::class)
        fun getFromString(value: String): PaymentType {
            return when (value) {
                "CASH"      -> CASH
                "DEBIT"     -> DEBIT
                "CREDIT"    -> CREDIT
                else -> throw Exception("The value is not supported")
            }
        }

        fun getString(value: PaymentType): String {
            return when (value) {
                CASH    -> "CASH"
                DEBIT   -> "DEBIT"
                CREDIT  -> "CREDIT"
            }
        }

        fun getAll(): List<PaymentType>{
            return listOf(CREDIT, DEBIT, CASH)
        }
    }
}
