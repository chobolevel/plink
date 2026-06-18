package com.plink.core.common.config

import com.plink.core.common.property.JwtProperties
import com.plink.core.common.property.RedisProperties
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.time.OffsetDateTime
import java.util.Optional

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
@EnableJpaRepositories(basePackages = ["com.plink"])
@EntityScan(basePackages = ["com.plink"])
@EnableConfigurationProperties(JwtProperties::class, RedisProperties::class)
class JpaConfiguration {

    @Bean
    fun auditingDateTimeProvider(): DateTimeProvider {
        return DateTimeProvider {
            Optional.of(OffsetDateTime.now())
        }
    }
}
