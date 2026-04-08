package com.plink.api.infrastructure.persistence.repository

import com.plink.core.domain.repository.CacheRepository
import com.plink.core.infrastructure.properties.JwtProperties
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
