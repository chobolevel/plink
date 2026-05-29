package com.plink.core.region.repository

import com.plink.core.region.entity.Region
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Component

@Component
class RegionQueryDslRepository : QuerydslRepositorySupport(Region::class.java)
