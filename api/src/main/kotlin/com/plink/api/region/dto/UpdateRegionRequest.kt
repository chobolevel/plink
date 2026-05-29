package com.plink.api.region.dto

import com.plink.core.region.vo.RegionUpdateMask
import jakarta.validation.constraints.NotEmpty

data class UpdateRegionRequest(
    val parentId: String?,
    val name: String?,
    val sortOrder: Int?,
    @field:NotEmpty(message = "update_mask는 필수 값입니다.")
    val updateMask: List<RegionUpdateMask>
)
