package com.plink.user

import com.plink.core.dto.JwtResponse
import com.plink.core.exception.BadCredentialException
import com.plink.core.exception.ErrorCode
import com.plink.core.jwt.TokenProvider
import com.plink.user.application.AuthService
import com.plink.user.application.dto.LoginCommonUserRequest
import com.plink.user.domain.model.User
import com.plink.user.domain.model.UserSignUpType
import com.plink.user.domain.repository.UserRepository
import com.plink.user.domain.service.UserPasswordEncoder
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@DisplayName("AuthService unit test")
@ExtendWith(MockitoExtension::class)
class AuthServiceTest {

    private val dummyUser: User = DummyUser.toEntity()

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var userPasswordEncoder: UserPasswordEncoder

    @Mock
    private lateinit var tokenProvider: TokenProvider

    @InjectMocks
    private lateinit var authService: AuthService

    @Test
    fun `일반 회원 로그인`() {
        // given
        val dummyJwtResponse = JwtResponse(
            accessToken = "access-token",
            accessTokenExpiredAt = 0L,
            refreshToken = "refresh-token",
            refreshTokenExpiredAt = 0L
        )
        val request: LoginCommonUserRequest = DummyUser.toLoginCommonUserRequest()

        `when`(
            userRepository.findByEmailAndSignUpType(
                email = request.email,
                signUpType = UserSignUpType.COMMON
            )
        ).thenReturn(dummyUser)
        `when`(
            userPasswordEncoder.match(
                rawPassword = request.password,
                encodedPassword = dummyUser.password!!
            )
        )
        `when`(
            tokenProvider.generateToken(
                userId = dummyUser.id!!
            )
        ).thenReturn(dummyJwtResponse)

        // when
        val result: JwtResponse = authService.loginUser(request = request)

        // then
        assertThat(result.accessToken).isEqualTo("access-token")
        assertThat(result.refreshToken).isEqualTo("refresh-token")
    }

    @Test
    fun `존재하지 않는 회원 로그인 시 예외 발생`() {
        // given
        val request: LoginCommonUserRequest = DummyUser.toLoginCommonUserRequest()
        `when`(
            userRepository.findByEmailAndSignUpType(
                email = request.email,
                signUpType = UserSignUpType.COMMON
            )
        ).thenThrow(
            BadCredentialException(
                code = ErrorCode.BAD_CREDENTIAL,
                message = ErrorCode.BAD_CREDENTIAL.koreanMessage
            )
        )

        // when & them
        assertThatThrownBy { authService.loginUser(request = request) }
            .isInstanceOf(BadCredentialException::class.java)
            .hasMessage(ErrorCode.BAD_CREDENTIAL.koreanMessage)
    }

    @Test
    fun `로그인 시 비밀번호 일치하지 않아 예외 발생`() {
        // given
        val request: LoginCommonUserRequest = DummyUser.toLoginCommonUserRequest()
        `when`(
            userRepository.findByEmailAndSignUpType(
                email = request.email,
                signUpType = UserSignUpType.COMMON
            )
        ).thenReturn(dummyUser)
        `when`(
            userPasswordEncoder.match(
                rawPassword = request.password,
                encodedPassword = dummyUser.password!!
            )
        ).thenThrow(
            BadCredentialException(
                code = ErrorCode.BAD_CREDENTIAL,
                message = ErrorCode.BAD_CREDENTIAL.koreanMessage
            )
        )

        // when & then
        assertThatThrownBy { authService.loginUser(request = request) }
            .isInstanceOf(BadCredentialException::class.java)
            .hasMessage(ErrorCode.BAD_CREDENTIAL.koreanMessage)
    }
}
