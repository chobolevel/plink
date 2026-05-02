package com.plink.api.user.application

import com.plink.api.user.application.dto.LoginCommonUserRequest
import com.plink.api.user.application.dto.LoginSocialUserRequest
import com.plink.core.common.domain.exception.BadCredentialException
import com.plink.core.common.domain.exception.ErrorCode
import com.plink.core.common.domain.exception.UnAuthorizedException
import com.plink.core.common.domain.repository.CacheRepository
import com.plink.core.common.infrastructure.security.TokenProvider
import com.plink.core.common.presentation.dto.JwtResponse
import com.plink.core.user.domain.model.User
import com.plink.core.user.domain.model.UserSignUpType
import com.plink.core.user.domain.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val tokenProvider: TokenProvider,
    private val cacheRepository: CacheRepository
) {

    // 부가 로직이 트랜잭션에 잡히므로 repository 트랜잭션 위임하는 것도 방법(붚필요한 커넥션 소유)
    // readOnly = true: DB는 조회만 수행, cacheRepository는 JPA 트랜잭션 범위 밖(Redis)
    @Transactional(readOnly = true)
    fun loginUser(request: LoginCommonUserRequest): JwtResponse {
        val user: User = userRepository.findByEmailAndSignUpType(
            email = request.email,
            signUpType = UserSignUpType.COMMON
        ) ?: throw BadCredentialException(
            code = ErrorCode.BAD_CREDENTIAL,
            message = ErrorCode.BAD_CREDENTIAL.koreanMessage
        )

        if (!passwordEncoder.matches(
                request.password,
                user.password
            )
        ) {
            throw BadCredentialException(
                code = ErrorCode.BAD_CREDENTIAL,
                message = ErrorCode.BAD_CREDENTIAL.koreanMessage
            )
        }

        val jwtResponse: JwtResponse = tokenProvider.generateToken(userId = user.id!!)
        cacheRepository.saveRefreshToken(
            userId = user.id!!,
            refreshToken = jwtResponse.refreshToken
        )

        return jwtResponse
    }

    @Transactional(readOnly = true)
    fun loginSocialUser(request: LoginSocialUserRequest): JwtResponse {
        val user: User = userRepository.findByEmailAndSocialIdAndSignUpType(
            email = request.email,
            socialId = request.socialId,
            signUpType = request.signUpType
        ) ?: throw BadCredentialException(
            code = ErrorCode.BAD_CREDENTIAL,
            message = ErrorCode.BAD_CREDENTIAL.koreanMessage
        )

        val jwtResponse: JwtResponse = tokenProvider.generateToken(userId = user.id!!)
        cacheRepository.saveRefreshToken(
            userId = user.id!!,
            refreshToken = jwtResponse.refreshToken
        )

        return jwtResponse
    }

    @Transactional(readOnly = true)
    fun reissue(refreshToken: String): JwtResponse {
        val isValidRefreshToken: Boolean = tokenProvider.validateToken(token = refreshToken)
        if (!isValidRefreshToken) {
            throw UnAuthorizedException(
                code = ErrorCode.INVALID_TOKEN,
                message = ErrorCode.INVALID_TOKEN.koreanMessage
            )
        }
        val userId: String = cacheRepository.findUserIdByRefreshToken(refreshToken = refreshToken) ?: throw UnAuthorizedException(
            code = ErrorCode.INVALID_TOKEN,
            message = ErrorCode.INVALID_TOKEN.koreanMessage
        )
        val jwtResponse: JwtResponse = tokenProvider.generateToken(userId = userId)
        // 현재 access/refresh 토큰이 모두 새롭게 갱신되는데 이부분에 대해서도 고민이 필요함(무한 로그인, rotate)
        cacheRepository.deleteRefreshToken(refreshToken = refreshToken)
        cacheRepository.saveRefreshToken(
            userId = userId,
            refreshToken = jwtResponse.refreshToken
        )
        return jwtResponse
    }

    fun logout(refreshToken: String): Boolean {
        val isValidRefreshToken: Boolean = tokenProvider.validateToken(token = refreshToken)
        if (!isValidRefreshToken) {
            throw UnAuthorizedException(
                code = ErrorCode.INVALID_TOKEN,
                message = ErrorCode.INVALID_TOKEN.koreanMessage
            )
        }
        cacheRepository.deleteRefreshToken(refreshToken = refreshToken)
        return true
    }
}
