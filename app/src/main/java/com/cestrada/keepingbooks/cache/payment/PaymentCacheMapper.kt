package com.cestrada.keepingbooks.cache

import com.cestrada.keepingbooks.cache.payment.PaymentCacheEntity
import com.cestrada.keepingbooks.model.Payment
import com.cestrada.keepingbooks.model.PaymentType
import com.cestrada.keepingbooks.util.EntityMappers
import com.cestrada.keepingbooks.util.SimpleStringDate
import javax.inject.Inject

class PaymentCacheMapper @Inject constructor(): EntityMappers<PaymentCacheEntity, Payment> {

    override fun fromEntity(entity: PaymentCacheEntity): Payment {
        return Payment(
                id = entity.id,
                description = entity.description,
                total = entity.total,
                payment_type = PaymentType.getFromString(entity.payment_type),
                date = SimpleStringDate(entity.date)
        )
    }

    override fun fromDomainModel(domainModel: Payment): PaymentCacheEntity {
        return PaymentCacheEntity(
                id = domainModel.id,
                description = domainModel.description,
                total = domainModel.total,
                payment_type = PaymentType.getString(domainModel.payment_type),
                date = domainModel.toString()
        )
    }

    fun mapFromEntityList(entities: List<PaymentCacheEntity>): List<Payment> =
            entities.map { fromEntity(it) }

}