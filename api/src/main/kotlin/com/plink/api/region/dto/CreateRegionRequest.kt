package com.plink.api.region.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class CreateRegionRequest(
    val parentId: String?,
    @field:NotEmpty(message = "지역 이름은 필수 값입니다.")
    val name: String,
    @field:NotNull(message = "지역 정렬 순서는 필수 값입니다.")
    val sortOrder: Int,
)
