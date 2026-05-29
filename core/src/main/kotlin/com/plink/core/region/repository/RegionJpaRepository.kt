package com.plink.core.region.repository

import com.plink.core.region.entity.Region
import org.springframework.data.jpa.repository.JpaRepository

interface RegionJpaRepository : JpaRepository<Region, String> {

    fun findByIdAndIsDeletedFalse(id: String): Region?
}
