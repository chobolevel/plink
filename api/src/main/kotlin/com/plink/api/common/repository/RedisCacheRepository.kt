package com.plink.api.common.repository

import com.plink.core.common.property.JwtProperties
import com.plink.core.common.repository.CacheRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisCacheRepository(
    private val jwtProperties: JwtProperties,
    private val redisTemplate: RedisTemplate<String, String>
) : CacheRepository {

    private val opsForHash = redisTemplate.opsForHash<String, String>()

    override fun saveRefreshToken(userId: String, refreshToken: String) {
        opsForHash.put(jwtProperties.cacheKey, refreshToken, userId)
    }

    override fun findUserIdByRefreshToken(refreshToken: String): String? {
        return opsForHash.get(jwtProperties.cacheKey, refreshToken)
    }

    override fun deleteRefreshToken(refreshToken: String) {
        opsForHash.delete(jwtProperties.cacheKey, refreshToken)
    }

    override fun set(key: String, value: String, duration: Long) {
        redisTemplate.opsForValue().set(key, value, duration)
    }

    override fun get(key: String): String? {
        return redisTemplate.opsForValue().get(key)
    }
}
