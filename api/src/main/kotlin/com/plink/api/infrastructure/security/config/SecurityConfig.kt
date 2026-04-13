package com.plink.api.infrastructure.security.config

import com.plink.api.infrastructure.security.filter.OnceAuthorizeFilter
import com.plink.core.infrastructure.security.TokenProvider
import com.plink.user.domain.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableMethodSecurity
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    private val userRepository: UserRepository
) {

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("POST", "GET", "PUT", "PATCH", "DELETE", "OPTIONS")
        configuration.allowedHeaders =
            listOf(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers",
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials",
                "Cookie",
            )
        configuration.exposedHeaders =
            listOf(
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials",
                "Authorization",
                "Content-Disposition",
                "Set-Cookie",
            )
        configuration.maxAge = 3600
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

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
