package com.plink.api.region.updater

import com.plink.api.region.dto.UpdateRegionRequest
import com.plink.core.region.entity.Region
import com.plink.core.region.repository.RegionRepository
import com.plink.core.region.vo.RegionUpdateMask
import org.springframework.stereotype.Component

@Component
class RegionUpdater(
    private val regionRepository: RegionRepository
) {

    fun markAsUpdate(request: UpdateRegionRequest, region: Region): Region {
        request.updateMask.forEach {
            when (it) {
                RegionUpdateMask.PARENT -> {
                    request.parentId?.let { parentId ->
                        val parentRegion: Region = regionRepository.findById(id = parentId)
                        region.assignParent(region = parentRegion)
                    }
                }
                RegionUpdateMask.NAME -> {
                    request.name?.let { name ->
                        region.name = name
                    }
                }
                RegionUpdateMask.SORT_ORDER -> {
                    request.sortOrder?.let { sortOrder ->
                        region.sortOrder = sortOrder
                    }
                }
            }
        }
        return region
    }
}
