package com.plink.core.common.presentation.dto

data class JwtResponse(
    val accessToken: String,
    val accessTokenExpiredAt: Long,
    val refreshToken: String,
    val refreshTokenExpiredAt: Long,
)
