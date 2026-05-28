package com.plink.user

import com.plink.api.user.dto.AddUserPermissionRequest
import com.plink.api.user.service.UserPermissionService
import com.plink.api.user.converter.UserPermissionConverter
import com.plink.core.common.exception.DataNotFoundException
import com.plink.core.common.exception.ErrorCode
import com.plink.core.user.entity.User
import com.plink.core.user.entity.UserPermission
import com.plink.core.user.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@DisplayName("UserPermissionService unit test")
@ExtendWith(MockitoExtension::class)
class UserPermissionServiceTest {

    private val dummyUser: User = DummyUser.toEntity()

    private val dummyUserPermission: UserPermission = DummyUserPermission.toEntity()

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var userPermissionConverter: UserPermissionConverter

    @InjectMocks
    private lateinit var userPermissionService: UserPermissionService

    @Test
    fun `회원 권한 추가`() {
        // given
        val dummyUserId: String = dummyUser.id!!
        val request: AddUserPermissionRequest = DummyUserPermission.toAddRequest()
        `when`(userRepository.findById(id = dummyUserId)).thenReturn(dummyUser)
        `when`(userPermissionConverter.toEntity(request = request)).thenReturn(dummyUserPermission)

        // when
        val result: Boolean = userPermissionService.addUserPermission(
            userId = dummyUserId,
            request = request
        )

        // then
        assertThat(result).isTrue
    }

    @Test
    fun `존재하지 않는 회원 권한 추가 시 예외 발생`() {
        // given
        val dummyUserId: String = dummyUser.id!!
        val request: AddUserPermissionRequest = DummyUserPermission.toAddRequest()
        `when`(userRepository.findById(id = dummyUserId)).thenThrow(
            DataNotFoundException(
                code = ErrorCode.USER_NOT_FOUND,
                message = ErrorCode.USER_NOT_FOUND.koreanMessage
            )
        )

        // when & then
        assertThatThrownBy {
            userPermissionService.addUserPermission(
                userId = dummyUserId,
                request = request
            )
        }
            .isInstanceOf(DataNotFoundException::class.java)
            .hasMessage(ErrorCode.USER_NOT_FOUND.koreanMessage)
    }

    @Test
    fun `회원 권한 제거`() {
        // given
        val dummyUserId: String = dummyUser.id!!
        val dummyUserPermissionId: String = dummyUserPermission.id!!
        `when`(userRepository.findById(id = dummyUserId)).thenReturn(dummyUser)

        // when
        val result: Boolean = userPermissionService.subUserPermission(
            userId = dummyUserId,
            userPermissionId = dummyUserPermissionId
        )

        // then
        assertThat(result).isTrue
    }

    @Test
    fun `존재하지 않는 회원 권한 제거 시 예외 발생`() {
        // given
        val dummyUserId: String = dummyUser.id!!
        val dummyUserPermissionId: String = dummyUserPermission.id!!
        `when`(userRepository.findById(id = dummyUserId)).thenThrow(
            DataNotFoundException(
                code = ErrorCode.USER_NOT_FOUND,
                message = ErrorCode.USER_NOT_FOUND.koreanMessage
            )
        )

        // when & then
        assertThatThrownBy {
            userPermissionService.subUserPermission(
                userId = dummyUserId,
                userPermissionId = dummyUserPermissionId
            )
        }
            .isInstanceOf(DataNotFoundException::class.java)
            .hasMessage(ErrorCode.USER_NOT_FOUND.koreanMessage)
    }
}
