package com.plink.core.region.repository

import com.plink.core.common.dto.Paging
import com.plink.core.region.entity.QRegion.region
import com.plink.core.region.entity.Region
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Component

@Component
class RegionQueryDslRepository : QuerydslRepositorySupport(Region::class.java) {

    fun searchRegions(
        booleanExpressions: Array<BooleanExpression>,
        paging: Paging,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<Region> {
        return from(region)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(paging.offset)
            .limit(paging.limit)
            .fetch()
    }

    fun searchRegionsCount(booleanExpressions: Array<BooleanExpression>): Long {
        return from(region)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
