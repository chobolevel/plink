package com.plink.core.domain.repository

interface CacheRepository {
    fun saveRefreshToken(userId: String, refreshToken: String)

    fun findUserIdByRefreshToken(refreshToken: String): String?

    fun deleteRefreshToken(refreshToken: String)
}
