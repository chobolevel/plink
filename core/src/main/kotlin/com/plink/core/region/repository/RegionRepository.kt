package com.plink.core.region.repository

import com.plink.core.common.dto.Paging
import com.plink.core.region.entity.Region
import com.plink.core.region.vo.RegionOrderType

interface RegionRepository {

    fun save(region: Region): Region

    fun findById(id: String): Region

    fun searchRegions(
        queryFilter: RegionQueryFilter,
        paging: Paging,
        orderTypes: List<RegionOrderType>
    ): List<Region>

    fun searchRegionsCount(queryFilter: RegionQueryFilter): Long

    fun findAllByParentId(parentId: String?): List<Region>
}
