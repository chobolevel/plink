package com.plink.api.region.converter

import com.plink.api.region.dto.CreateRegionRequest
import com.plink.api.region.dto.RegionResponse
import com.plink.core.common.extension.toMillis
import com.plink.core.region.entity.Region
import org.springframework.stereotype.Component

@Component
class RegionConverter {

    fun toEntity(request: CreateRegionRequest): Region {
        return Region(
            name = request.name,
            sortOrder = request.sortOrder
        )
    }

    fun toResponse(region: Region): RegionResponse {
        return RegionResponse(
            id = region.id!!,
            name = region.name,
            sortOrder = region.sortOrder,
            createdAt = region.createdAt!!.toMillis(),
            updatedAt = region.updatedAt!!.toMillis()
        )
    }

    fun toResponseInBatch(regions: List<Region>): List<RegionResponse> {
        return regions.map { toResponse(it) }
    }
}
