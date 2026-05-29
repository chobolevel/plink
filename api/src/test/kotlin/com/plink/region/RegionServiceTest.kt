package com.plink.region

import com.plink.api.region.assembler.RegionAssembler
import com.plink.api.region.converter.RegionConverter
import com.plink.api.region.dto.CreateRegionRequest
import com.plink.api.region.dto.RegionResponse
import com.plink.api.region.service.RegionService
import com.plink.core.common.exception.DataNotFoundException
import com.plink.core.common.exception.ErrorCode
import com.plink.core.region.entity.Region
import com.plink.core.region.repository.RegionRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@DisplayName("Region service unit test")
class RegionServiceTest {

    private val dummyRegion: Region = DummyRegion.toEntity()

    private val dummyParentRegion: Region = DummyRegion.toEntity()

    private val dummyRegionResponse: RegionResponse = DummyRegion.toResponse()

    private val dummyParentRegionResponse: RegionResponse = DummyRegion.toParentResponse()

    @Mock
    private lateinit var regionConverter: RegionConverter

    @Mock
    private lateinit var regionAssembler: RegionAssembler

    @Mock
    private lateinit var regionRepository: RegionRepository

    @InjectMocks
    private lateinit var regionService: RegionService

    @Test
    fun `지역 생성 테스트`() {
        // given
        val request: CreateRegionRequest = DummyRegion.toCreateRequest()
        `when`(regionConverter.toEntity(request = request)).thenReturn(dummyRegion)
        `when`(regionRepository.findById(id = request.parentId!!)).thenReturn(dummyParentRegion)
        `when`(
            regionAssembler.assemble(
                region = dummyRegion,
                parentRegion = dummyParentRegion,
            )
        ).thenReturn(dummyRegion)
        `when`(regionRepository.save(region = dummyRegion)).thenReturn(dummyRegion)

        // when
        val result: String = regionService.createRegion(request = request)

        // then
        assertThat(result).isEqualTo(dummyRegion.id)
    }

    @Test
    fun `부모 지역이 존재하지 않는 경우 지역 생성 시 예외 발생`() {
        // given
        val request: CreateRegionRequest = DummyRegion.toCreateRequest()
        `when`(regionConverter.toEntity(request = request)).thenReturn(dummyRegion)
        `when`(regionRepository.findById(id = request.parentId!!)).thenThrow(
            DataNotFoundException(
                code = ErrorCode.REGION_NOT_FOUND,
                message = ErrorCode.REGION_NOT_FOUND.koreanMessage
            )
        )

        // when & then
        assertThatThrownBy { regionService.createRegion(request = request) }
            .isInstanceOf(DataNotFoundException::class.java)
            .hasMessage(ErrorCode.REGION_NOT_FOUND.koreanMessage)
    }

    @Test
    fun `지역 목록 조회`() {
        // given
        val dummyRegions: List<Region> = listOf(dummyParentRegion)
        val dummyRegionResponses: List<RegionResponse> = listOf(dummyParentRegionResponse)
        `when`(regionRepository.findAllByParentId(parentId = null)).thenReturn(dummyRegions)
        `when`(regionConverter.toResponseInBatch(regions = dummyRegions)).thenReturn(dummyRegionResponses)

        // when
        val result: List<RegionResponse> = regionService.getRegions(parentId = null)

        // then
        assertThat(result).isEqualTo(dummyRegionResponses)
    }

    @Test
    fun `하위 지역 목록 조회`() {
        // given
        val dummyParentRegionId: String = dummyParentRegion.id!!
        val dummyRegions: List<Region> = listOf(dummyRegion)
        val dummyRegionResponses: List<RegionResponse> = listOf(dummyRegionResponse)
        `when`(regionRepository.findAllByParentId(parentId = dummyParentRegionId)).thenReturn(dummyRegions)
        `when`(regionConverter.toResponseInBatch(regions = dummyRegions)).thenReturn(dummyRegionResponses)

        // when
        val result: List<RegionResponse> = regionService.getRegions(parentId = dummyParentRegionId)

        // then
        assertThat(result).isEqualTo(dummyRegionResponses)
    }
}
