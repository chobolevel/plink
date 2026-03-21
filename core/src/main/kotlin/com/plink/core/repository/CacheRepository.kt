package com.plink.core.repository

import com.plink.core.properties.JwtProperties
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class CacheRepository(
    val jwtProperties: JwtProperties,
    val redisTemplate: RedisTemplate<String, String>
) {

    private val opsForHash = redisTemplate.opsForHash<String, String>()

    fun saveRefreshToken(userId: String, refreshToken: String) {
        opsForHash.put(jwtProperties.cacheKey, refreshToken, userId)
    }

    fun findUserIdByRefreshToken(refreshToken: String): String? {
        return opsForHash.get(jwtProperties.cacheKey, refreshToken)
    }
}
