package com.plink.user

import com.plink.core.dto.JwtResponse
import com.plink.core.exception.BadCredentialException
import com.plink.core.exception.ErrorCode
import com.plink.core.exception.UnAuthorizedException
import com.plink.core.jwt.TokenProvider
import com.plink.core.repository.CacheRepository
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
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@DisplayName("AuthService unit test")
@ExtendWith(MockitoExtension::class)
class AuthServiceTest {

    private val dummyUser: User = DummyUser.toEntity()

    val dummyJwtResponse = JwtResponse(
        accessToken = "access-token",
        accessTokenExpiredAt = 0L,
        refreshToken = "refresh-token",
        refreshTokenExpiredAt = 0L
    )

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var userPasswordEncoder: UserPasswordEncoder

    @Mock
    private lateinit var tokenProvider: TokenProvider

    @Mock
    private lateinit var cacheRepository: CacheRepository

    @InjectMocks
    private lateinit var authService: AuthService

    @Test
    fun `일반 회원 로그인`() {
        // given
        val request: LoginCommonUserRequest = DummyUser.toLoginCommonUserRequest()
        `when`(
            userRepository.findByEmailAndSignUpType(
                email = request.email,
                signUpType = UserSignUpType.COMMON
            )
        ).thenReturn(dummyUser)
        doNothing().`when`(userPasswordEncoder).match(
            rawPassword = request.password,
            encodedPassword = dummyUser.password!!
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

    @Test
    fun `토큰 갱신`() {
        // given
        val dummyUserId: String = dummyUser.id!!
        val dummyRefreshToken = "refresh-token"
        `when`(tokenProvider.validateToken(token = dummyRefreshToken)).thenReturn(true)
        `when`(cacheRepository.findUserIdByRefreshToken(refreshToken = dummyRefreshToken)).thenReturn(dummyUserId)
        `when`(tokenProvider.generateToken(userId = dummyUserId)).thenReturn(dummyJwtResponse)

        // when
        val result: JwtResponse = authService.reissue(refreshToken = dummyRefreshToken)

        // then
        assertThat(result.accessToken).isEqualTo("access-token")
        assertThat(result.refreshToken).isEqualTo("refresh-token")
    }

    @Test
    fun `토큰 갱신 시 유효하지 않은 토큰 예외 발생`() {
        // given
        val dummyRefreshToken = "refresh-token"
        `when`(tokenProvider.validateToken(token = dummyRefreshToken)).thenReturn(false)

        // when & then
        assertThatThrownBy { authService.reissue(refreshToken = dummyRefreshToken) }
            .isInstanceOf(UnAuthorizedException::class.java)
            .hasMessage(ErrorCode.INVALID_TOKEN.koreanMessage)
    }
}
