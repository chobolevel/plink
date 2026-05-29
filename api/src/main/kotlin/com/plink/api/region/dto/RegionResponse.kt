package com.plink.api.region.dto

data class RegionResponse(
    val id: String,
    val name: String,
    val sortOrder: Int,
    val createdAt: Long,
    val updatedAt: Long
)
