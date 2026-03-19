package com.plink.user.application

import com.plink.core.dto.JwtResponse
import com.plink.core.exception.BadCredentialException
import com.plink.core.exception.ErrorCode
import com.plink.core.jwt.TokenProvider
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
    private val tokenProvider: TokenProvider
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

        return tokenProvider.generateToken(userId = user.id!!)
    }
}
