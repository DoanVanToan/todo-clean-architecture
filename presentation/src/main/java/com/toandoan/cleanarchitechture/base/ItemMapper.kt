package com.toandoan.cleanarchitechture.base

import com.toandoan.cleanarchitechture.model.ItemModel
import com.toandoan.domain.model.DomainModel

interface ItemMapper<DM : DomainModel, IM : ItemModel> {
    fun mapToPresentation(domainModel: DM): IM

    fun mapToDomain(itemModel: IM): DM
}