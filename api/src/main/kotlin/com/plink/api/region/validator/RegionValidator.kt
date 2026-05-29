package com.plink.api.region.validator

import com.plink.api.region.dto.UpdateRegionRequest
import com.plink.core.common.exception.ErrorCode
import com.plink.core.common.exception.InvalidParameterException
import com.plink.core.common.exception.PolicyViolationException
import com.plink.core.region.repository.RegionRepository
import com.plink.core.region.vo.RegionUpdateMask
import org.springframework.stereotype.Component

@Component
class RegionValidator(
    private val regionRepository: RegionRepository
) {

    fun validate(regionId: String, request: UpdateRegionRequest) {
        request.updateMask.forEach {
            when (it) {
                RegionUpdateMask.PARENT -> {
                    if (!request.parentId.isNullOrEmpty()) {
                        if (regionId == request.parentId) {
                            throw PolicyViolationException(
                                code = ErrorCode.CIRCULAR_REGION,
                                message = ErrorCode.CIRCULAR_REGION.koreanMessage
                            )
                        }
                    }
                }
                RegionUpdateMask.NAME -> {
                    if (request.name.isNullOrEmpty()) {
                        throw InvalidParameterException(
                            code = ErrorCode.INVALID_PARAMETER,
                            message = "지역명은 필수 값입니다."
                        )
                    }
                }
                RegionUpdateMask.SORT_ORDER -> {
                    if (request.sortOrder == null) {
                        throw InvalidParameterException(
                            code = ErrorCode.INVALID_PARAMETER,
                            message = "정렬 순서는 필수 값입니다."
                        )
                    }
                }
            }
        }
    }
}
