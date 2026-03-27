package com.plink.api.infrastructure.persistence

import com.plink.core.properties.JwtProperties
import com.plink.core.repository.CacheRepository
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
}
