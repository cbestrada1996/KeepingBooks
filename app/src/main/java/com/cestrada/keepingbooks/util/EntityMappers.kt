package com.cestrada.keepingbooks.util

interface EntityMappers<Entity, DomainModel> {
    fun fromEntity(entity: Entity): DomainModel
    fun fromDomainModel(domainModel: DomainModel): Entity
}