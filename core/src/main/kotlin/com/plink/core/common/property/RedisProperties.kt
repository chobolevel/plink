package com.plink.core.common.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "redis.config")
data class RedisProperties(
    val host: String,
    val port: Int,
    val password: String,
)
