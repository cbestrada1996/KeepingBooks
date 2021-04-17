package com.cestrada.keepingbooks.model

data class Payment(
        var id: Int,
        var description: String,
        var total: Float,
        var payment_type: PaymentType,
        var date: String
)

enum class PaymentType {
    CREDIT, DEBIT, CASH
}
