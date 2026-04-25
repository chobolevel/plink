package com.plink.user

import com.plink.api.user.application.AuthService
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@DisplayName("AuthService unit test")
@ExtendWith(MockitoExtension::class)
class AuthServiceTest {

    private val dummyUser: User = DummyUser.toEntity()

    private val dummySocialUser: User = DummyUser.toSocialUserEntity()

    val dummyJwtResponse = JwtResponse(
        accessToken = "access-token",
        accessTokenExpiredAt = 0L,
        refreshToken = "refresh-token",
        refreshTokenExpiredAt = 0L
    )

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var passwordEncoder: BCryptPasswordEncoder

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
        `when`(
            passwordEncoder.matches(
                request.password,
                dummyUser.password,
            )
        ).thenReturn(true)
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
            passwordEncoder.matches(
                request.password,
                dummyUser.password!!
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
    fun `소셜 회원 로그인`() {
        // given
        val request: LoginSocialUserRequest = DummyUser.toLoginSocialUserRequest()
        `when`(
            userRepository.findByEmailAndSocialIdAndSignUpType(
                email = request.email,
                socialId = request.socialId,
                signUpType = request.signUpType
            )
        ).thenReturn(dummySocialUser)
        `when`(tokenProvider.generateToken(userId = dummySocialUser.id!!)).thenReturn(dummyJwtResponse)

        // when
        val result: JwtResponse = authService.loginSocialUser(request = request)

        // then
        assertThat(result.accessToken).isEqualTo("access-token")
        assertThat(result.refreshToken).isEqualTo("refresh-token")
    }

    @Test
    fun `소설 회원 로그인 시 존재하지 않는 회원 예외 발생`() {
        // given
        val request: LoginSocialUserRequest = DummyUser.toLoginSocialUserRequest()
        `when`(
            userRepository.findByEmailAndSocialIdAndSignUpType(
                email = request.email,
                socialId = request.socialId,
                signUpType = request.signUpType
            )
        ).thenThrow(
            BadCredentialException(
                code = ErrorCode.BAD_CREDENTIAL,
                message = ErrorCode.BAD_CREDENTIAL.koreanMessage
            )
        )

        // when & then
        assertThatThrownBy { authService.loginSocialUser(request = request) }
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

    @Test
    fun `토큰 갱신 시 저장되지 않은 토큰 이슈`() {
        // given
        val dummyRefreshToken = "refresh-token"
        `when`(tokenProvider.validateToken(token = dummyRefreshToken)).thenReturn(true)
        `when`(cacheRepository.findUserIdByRefreshToken(refreshToken = dummyRefreshToken)).thenReturn(null)

        // when & then
        assertThatThrownBy { authService.reissue(refreshToken = dummyRefreshToken) }
            .isInstanceOf(UnAuthorizedException::class.java)
            .hasMessage(ErrorCode.INVALID_TOKEN.koreanMessage)
    }

    @Test
    fun `로그아웃`() {
        // given
        val dummyRefreshToken = "refresh-token"
        `when`(tokenProvider.validateToken(token = dummyRefreshToken)).thenReturn(true)
        doNothing().`when`(cacheRepository).deleteRefreshToken(
            refreshToken = dummyRefreshToken
        )

        // when
        val result: Boolean = authService.logout(refreshToken = dummyRefreshToken)

        // then
        assertThat(result).isTrue
    }
}
