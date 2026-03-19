package com.plink.api.config

import com.plink.api.filter.OnceAuthorizeFilter
import com.plink.core.jwt.TokenProvider
import com.plink.user.domain.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableMethodSecurity
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    private val userRepository: UserRepository
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            cors { }
            csrf { disable() }
            httpBasic { disable() }
            formLogin { disable() }
            authorizeHttpRequests {
                authorize(anyRequest, permitAll)
            }
            addFilterAfter<UsernamePasswordAuthenticationFilter>(
                OnceAuthorizeFilter(
                    tokenProvider = tokenProvider,
                    userRepository = userRepository
                )
            )
        }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
