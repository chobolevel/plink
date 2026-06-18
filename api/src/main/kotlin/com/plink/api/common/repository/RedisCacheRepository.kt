package com.plink.api.common.repository

import com.plink.core.common.property.JwtProperties
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RedisCacheRepository(
    private val jwtProperties: JwtProperties,
    private val redisTemplate: RedisTemplate<String, String>
) : CacheRepository {

    private val opsForValue = redisTemplate.opsForValue()

    override fun saveRefreshToken(userId: String, refreshToken: String) {
        opsForValue.set(
            refreshTokenKey(refreshToken),
            userId,
            jwtProperties.refreshTokenExpirySeconds,
            TimeUnit.SECONDS
        )
    }

    override fun findUserIdByRefreshToken(refreshToken: String): String? {
        return opsForValue.get(refreshTokenKey(refreshToken))
    }

    override fun deleteRefreshToken(refreshToken: String) {
        redisTemplate.delete(refreshTokenKey(refreshToken))
    }

    override fun set(key: String, value: String, duration: Long) {
        opsForValue.set(key, value, duration)
    }

    override fun get(key: String): String? {
        return opsForValue.get(key)
    }

    private fun refreshTokenKey(refreshToken: String): String = "${jwtProperties.cacheKey}:$refreshToken"
}
