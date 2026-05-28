package com.plink.core.common.dto

data class JwtResponse(
    val accessToken: String,
    val accessTokenExpiredAt: Long,
    val refreshToken: String,
    val refreshTokenExpiredAt: Long,
)
