package com.plink.core.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.time.OffsetDateTime
import java.util.Optional

// api 모듈에서 직접 의존하지 않지만 런타임에 해당 설정이 적용됨
@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
@EnableJpaRepositories(basePackages = ["com.plink"])
@EntityScan(basePackages = ["com.plink"])
class JpaConfiguration {

    @Bean
    fun auditingDateTimeProvider(): DateTimeProvider {
        return DateTimeProvider {
            Optional.of(OffsetDateTime.now())
        }
    }
}
