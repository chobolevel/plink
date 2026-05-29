package com.plink.core.region.repository

import com.plink.core.common.dto.Paging
import com.plink.core.common.exception.DataNotFoundException
import com.plink.core.common.exception.ErrorCode
import com.plink.core.region.entity.QRegion.region
import com.plink.core.region.entity.Region
import com.plink.core.region.vo.RegionOrderType
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component

@Component
class RegionRepositoryAdapter(
    private val regionJpaRepository: RegionJpaRepository,
    private val regionQueryDslRepository: RegionQueryDslRepository
) : RegionRepository {

    override fun save(region: Region): Region {
        return regionJpaRepository.save(region)
    }

    override fun findById(id: String): Region {
        return regionJpaRepository.findByIdAndIsDeletedFalse(id) ?: throw DataNotFoundException(
            code = ErrorCode.REGION_NOT_FOUND,
            message = ErrorCode.REGION_NOT_FOUND.koreanMessage
        )
    }

    override fun searchRegions(
        queryFilter: RegionQueryFilter,
        paging: Paging,
        orderTypes: List<RegionOrderType>
    ): List<Region> {
        return regionQueryDslRepository.searchRegions(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            paging = paging,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    override fun searchRegionsCount(queryFilter: RegionQueryFilter): Long {
        return regionQueryDslRepository.searchRegionsCount(booleanExpressions = queryFilter.toBooleanExpressions())
    }

    override fun findAllByParentId(parentId: String?): List<Region> {
        return if (parentId.isNullOrBlank()) {
            regionJpaRepository.findAllByParentIsNullAndIsDeletedFalseOrderBySortOrderAsc()
        } else {
            regionJpaRepository.findAllByParentIdAndIsDeletedFalseOrderBySortOrderAsc(parentId)
        }
    }

    private fun List<RegionOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                RegionOrderType.SORT_ORDER_ASC -> region.sortOrder.asc()
                RegionOrderType.SORT_ORDER_DESC -> region.sortOrder.desc()
                RegionOrderType.CREATED_AT_ASC -> region.createdAt.asc()
                RegionOrderType.CREATED_AT_DESC -> region.createdAt.desc()
            }
        }.toTypedArray()
    }
}
