package com.plink.api.region.converter

import com.plink.api.region.dto.CreateRegionRequest
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
}
