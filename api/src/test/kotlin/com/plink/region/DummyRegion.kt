package com.plink.region

import com.plink.api.region.dto.CreateRegionRequest
import com.plink.api.region.dto.RegionResponse
import com.plink.api.region.dto.UpdateRegionRequest
import com.plink.core.region.entity.Region
import com.plink.core.region.vo.RegionUpdateMask

object DummyRegion {
    private const val id: String = "dummyRegionId"
    private const val name: String = "강남구"
    private const val sortOrder: Int = 0
    private const val createdAt: Long = 0L
    private const val updatedAt: Long = 0L

    private const val parentId: String = "dummyParentRegionId"
    private const val parentName: String = "서울특별시"
    private const val parentSortOrder: Int = 0
    private const val parentCreatedAt: Long = 0L
    private const val parentUpdatedAt: Long = 0L

    private val dummyRegion: Region by lazy {
        Region(
            name = name,
            sortOrder = sortOrder,
        ).also { it.id = id }
    }

    private val dummyRegionResponse: RegionResponse by lazy {
        RegionResponse(
            id = id,
            name = name,
            sortOrder = sortOrder,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

    private val dummyParentRegionResponse: RegionResponse by lazy {
        RegionResponse(
            id = parentId,
            name = parentName,
            sortOrder = parentSortOrder,
            createdAt = parentCreatedAt,
            updatedAt = parentUpdatedAt
        )
    }

    private val dummyParentRegion: Region by lazy {
        Region(
            name = parentName,
            sortOrder = parentSortOrder
        ).also { it.id = parentId }
    }

    private val dummyCreateRequest: CreateRegionRequest = CreateRegionRequest(
        parentId = parentId,
        name = name,
        sortOrder = sortOrder,
    )

    private val dummyUpdateRequest: UpdateRegionRequest = UpdateRegionRequest(
        parentId = null,
        name = "경기도",
        sortOrder = null,
        updateMask = listOf(RegionUpdateMask.NAME)
    )

    fun toEntity(): Region = dummyRegion

    fun toParentRegion(): Region = dummyParentRegion

    fun toResponse(): RegionResponse = dummyRegionResponse

    fun toParentResponse(): RegionResponse = dummyParentRegionResponse

    fun toCreateRequest(): CreateRegionRequest = dummyCreateRequest

    fun toUpdateRequest(): UpdateRegionRequest = dummyUpdateRequest
}
