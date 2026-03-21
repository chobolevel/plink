package com.plink.user.application

import com.plink.core.dto.JwtResponse
import com.plink.core.exception.BadCredentialException
import com.plink.core.exception.ErrorCode
import com.plink.core.exception.UnAuthorizedException
import com.plink.core.jwt.TokenProvider
import com.plink.core.repository.CacheRepository
import com.plink.user.application.dto.LoginCommonUserRequest
import com.plink.user.domain.model.User
import com.plink.user.domain.model.UserSignUpType
import com.plink.user.domain.repository.UserRepository
import com.plink.user.domain.service.UserPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val userPasswordEncoder: UserPasswordEncoder,
    private val tokenProvider: TokenProvider,
    private val cacheRepository: CacheRepository
) {

    // 부가 로직이 트랜잭션에 잡히므로 repository 트랜잭션 위임하는 것도 방법(붚필요한 커넥션 소유)
    @Transactional(readOnly = true)
    fun loginUser(request: LoginCommonUserRequest): JwtResponse {
        val user: User = userRepository.findByEmailAndSignUpType(
            email = request.email,
            signUpType = UserSignUpType.COMMON
        ) ?: throw BadCredentialException(
            code = ErrorCode.BAD_CREDENTIAL,
            message = ErrorCode.BAD_CREDENTIAL.koreanMessage
        )

        userPasswordEncoder.match(
            rawPassword = request.password,
            encodedPassword = user.password!!
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
        // TODO 예외를 이걸로 던지는 게 맞을까?
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
        cacheRepository.deleteRefreshToken(refreshToken = refreshToken)
        cacheRepository.saveRefreshToken(
            userId = userId,
            refreshToken = jwtResponse.refreshToken
        )
        return jwtResponse
    }
}
