package com.plink.api.region.service

import com.plink.api.region.assembler.RegionAssembler
import com.plink.api.region.converter.RegionConverter
import com.plink.api.region.dto.CreateRegionRequest
import com.plink.api.region.dto.RegionResponse
import com.plink.core.region.entity.Region
import com.plink.core.region.repository.RegionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegionService(
    private val regionConverter: RegionConverter,
    private val regionAssembler: RegionAssembler,
    private val regionRepository: RegionRepository,
) {

    @Transactional
    fun createRegion(request: CreateRegionRequest): String {
        val region: Region = regionConverter.toEntity(request = request)
        val parentRegion: Region? = request.parentId?.let { regionRepository.findById(id = request.parentId) }
        val assembledRegion: Region = regionAssembler.assemble(
            region = region,
            parentRegion = parentRegion
        )
        return regionRepository.save(region = assembledRegion).id!!
    }

    @Transactional(readOnly = true)
    fun getRegions(parentId: String? = null): List<RegionResponse> {
        val regions: List<Region> = regionRepository.findAllByParentId(parentId = parentId)
        return regionConverter.toResponseInBatch(regions = regions)
    }

    @Transactional(readOnly = true)
    fun getRegion(regionId: String): RegionResponse {
        val region: Region = regionRepository.findById(regionId)
        return regionConverter.toResponse(region)
    }
}
