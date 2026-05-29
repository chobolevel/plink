package com.plink.api.region.assembler

import com.plink.core.region.entity.Region
import org.springframework.stereotype.Component

@Component
class RegionAssembler {

    fun assemble(region: Region, parentRegion: Region?): Region {
        parentRegion?.let { region.assignParent(region = parentRegion) }
        return region
    }
}
