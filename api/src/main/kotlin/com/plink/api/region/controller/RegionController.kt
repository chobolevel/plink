package com.plink.api.region.controller

import com.plink.api.region.dto.RegionResponse
import com.plink.api.region.service.RegionService
import com.plink.core.common.dto.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Region(지역)", description = "지역 관리 API")
@RestController
@RequestMapping("/api/v1")
class RegionController(
    private val regionService: RegionService
) {

    @Operation(summary = "지역 목록 조회 API")
    @GetMapping("/regions")
    fun getRegions(): ResponseEntity<ApiResponse> {
        val result: List<RegionResponse> = regionService.getRegions()
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @Operation(summary = "하위 지역 목록 조회 API")
    @GetMapping("/regions/{regionId}")
    fun getSubRegions(@PathVariable regionId: String): ResponseEntity<ApiResponse> {
        val result: List<RegionResponse> = regionService.getRegions(parentId = regionId)
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }
}
