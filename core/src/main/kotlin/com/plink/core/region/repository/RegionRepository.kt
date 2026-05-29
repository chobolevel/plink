package com.plink.core.region.repository

import com.plink.core.region.entity.Region

interface RegionRepository {

    fun save(region: Region): Region

    fun findById(id: String): Region
}
