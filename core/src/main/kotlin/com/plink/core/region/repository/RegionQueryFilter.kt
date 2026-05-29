package com.plink.core.region.repository

import com.plink.core.region.entity.QRegion.region
import com.querydsl.core.types.dsl.BooleanExpression

data class RegionQueryFilter(
    private val parentId: String?,
    private val name: String?,
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            parentId?.let { region.parent.id.eq(it) } ?: region.parent.isNull,
            name?.let { region.name.contains(it) },
            region.isDeleted.isFalse
        ).toTypedArray()
    }
}
