package com.toandoan.data.model

import com.toandoan.domain.model.DomainModel

abstract class EnityMapper<DM : DomainModel, EM : EnityModel> {
    abstract fun mapToDomain(enityModel: EM): DM

    abstract fun mapToEnity(domainModel: DM): EM
}