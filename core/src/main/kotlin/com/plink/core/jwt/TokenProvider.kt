package com.plink.core.jwt

import com.plink.core.dto.JwtResponse
import com.plink.core.properties.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.Date
import java.util.concurrent.TimeUnit

@Component
class TokenProvider(
    private val jwtProperties: JwtProperties,
) {

    fun generateToken(userId: String): JwtResponse {
        val now = Date()
        val accessTokenExpiredAt = Date(now.time + TimeUnit.MINUTES.toMillis(30))
        val refreshTokenExpiredAt = Date(now.time + TimeUnit.DAYS.toMillis(7))
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
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuer(jwtProperties.issuer)
            .setIssuedAt(issuedAt)
            .setExpiration(expiredAt)
            .setSubject(subject)
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secret)
            .compact()
    }

    fun getUserId(token: String): String {
        val claims: Claims = Jwts.parser()
            .setSigningKey(jwtProperties.secret)
            .parseClaimsJws(token)
            .body
        return claims.subject
    }

    fun validateToken(token: String): Boolean {
        return runCatching { getClaims(token = token) }.isSuccess
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(jwtProperties.secret)
            .parseClaimsJws(token)
            .body
    }
}
