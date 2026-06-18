package com.plink.api.common.repository

interface CacheRepository {
    fun saveRefreshToken(userId: String, refreshToken: String)

    fun findUserIdByRefreshToken(refreshToken: String): String?

    fun deleteRefreshToken(refreshToken: String)

    fun set(key: String, value: String, duration: Long)

    fun get(key: String): String?
}
