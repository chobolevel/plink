package com.plink.core.common.security

import com.plink.core.common.dto.JwtResponse
import com.plink.core.common.exception.ErrorCode
import com.plink.core.common.exception.UnAuthorizedException
import com.plink.core.common.property.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.Base64
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.crypto.SecretKey

@Component
class TokenProvider(
    private val jwtProperties: JwtProperties,
) {

    private val secretKey: SecretKey by lazy {
        val keyBytes = Base64.getDecoder().decode(jwtProperties.secret)
        Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateToken(userId: String): JwtResponse {
        val now = Date()
        val accessTokenExpiredAt = Date(now.time + TimeUnit.SECONDS.toMillis(jwtProperties.accessTokenExpirySeconds))
        val refreshTokenExpiredAt = Date(now.time + TimeUnit.SECONDS.toMillis(jwtProperties.refreshTokenExpirySeconds))
        val accessToken: String = generateJwtToken(
            subject = userId,
            issuedAt = now,
            expiredAt = accessTokenExpiredAt,
        )
        val refreshToken: String = generateJwtToken(
            subject = userId,
            issuedAt = now,
            expiredAt = refreshTokenExpiredAt,
        )
        return JwtResponse(
            accessToken = accessToken,
            accessTokenExpiredAt = accessTokenExpiredAt.toInstant().toEpochMilli(),
            refreshToken = refreshToken,
            refreshTokenExpiredAt = refreshTokenExpiredAt.toInstant().toEpochMilli(),
        )
    }

    private fun generateJwtToken(subject: String, issuedAt: Date, expiredAt: Date): String {
        return Jwts.builder()
            .issuer(jwtProperties.issuer)
            .issuedAt(issuedAt)
            .expiration(expiredAt)
            .subject(subject)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()
    }

    fun getUserId(token: String): String {
        return getClaims(token).subject
    }

    fun validateToken(token: String) {
        try {
            getClaims(token = token)
        } catch (e: ExpiredJwtException) {
            throw UnAuthorizedException(
                code = ErrorCode.EXPIRED_TOKEN,
                message = ErrorCode.EXPIRED_TOKEN.koreanMessage,
                throwable = e
            )
        } catch (e: JwtException) {
            throw UnAuthorizedException(
                code = ErrorCode.INVALID_TOKEN,
                message = ErrorCode.INVALID_TOKEN.koreanMessage,
                throwable = e
            )
        }
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }
}
