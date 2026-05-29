package com.plink.core.region.repository

import com.plink.core.common.exception.DataNotFoundException
import com.plink.core.common.exception.ErrorCode
import com.plink.core.region.entity.Region
import org.springframework.stereotype.Component

@Component
class RegionRepositoryAdapter(
    private val regionJpaRepository: RegionJpaRepository,
    private val regionQueryDslRepository: RegionQueryDslRepository
) : RegionRepository {

    override fun save(region: Region): Region {
        return regionJpaRepository.save(region)
    }

    override fun findById(id: String): Region {
        return regionJpaRepository.findByIdAndIsDeletedFalse(id) ?: throw DataNotFoundException(
            code = ErrorCode.REGION_NOT_FOUND,
            message = ErrorCode.REGION_NOT_FOUND.koreanMessage
        )
    }
}
