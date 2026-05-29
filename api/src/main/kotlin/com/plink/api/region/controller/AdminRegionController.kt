package com.plink.api.region.controller

import com.plink.api.common.annotation.AdminOnly
import com.plink.api.region.dto.CreateRegionRequest
import com.plink.api.region.service.RegionService
import com.plink.core.common.dto.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Region(지역)", description = "지역 관리 API")
@RestController
@RequestMapping("/api/v1/admin")
class AdminRegionController(
    private val regionService: RegionService
) {

    @AdminOnly
    @PostMapping("/regions")
    fun createRegion(
        @Valid @RequestBody
        request: CreateRegionRequest
    ): ResponseEntity<ApiResponse> {
        val result: String = regionService.createRegion(request)
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }
}
