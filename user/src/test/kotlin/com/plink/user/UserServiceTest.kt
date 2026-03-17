package com.plink.user

import com.plink.core.dto.ApiPagingResponse
import com.plink.core.dto.Paging
import com.plink.core.exception.DataNotFoundException
import com.plink.core.exception.ErrorCode
import com.plink.user.application.UserService
import com.plink.user.application.dto.CreateUserRequest
import com.plink.user.application.dto.UserResponse
import com.plink.user.domain.model.User
import com.plink.user.domain.model.UserOrderType
import com.plink.user.domain.repository.UserRepository
import com.plink.user.domain.service.UserConverter
import com.plink.user.infrastructure.persistence.UserQueryFilter
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@DisplayName("UserService unit test")
@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    private val dummyUser: User = DummyUser.toEntity()

    private val dummyUserResponse = DummyUser.toResponse()

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var userConverter: UserConverter

    @InjectMocks
    private lateinit var userService: UserService

    @Test
    fun `회원 생성`() {
        // given
        val request: CreateUserRequest = DummyUser.toCreateRequest()
        `when`(userConverter.toEntity(request = request)).thenReturn(dummyUser)
        `when`(userRepository.save(user = dummyUser)).thenReturn(dummyUser)

        // when
        val result: String = userService.createUser(request = request)

        // then
        assertThat(result).isEqualTo(dummyUser.id)
    }

    @Test
    fun `회원 목록 조회`() {
        // given
        val dummyUsers: List<User> = listOf(dummyUser)
        val dummyUserResponses: List<UserResponse> = listOf(dummyUserResponse)
        val queryFilter = UserQueryFilter(
            email = null,
            signUpType = null,
            nickname = null,
            role = null,
            isResigned = null
        )
        val paging = Paging(
            page = 1,
            size = 20
        )
        val orderTypes: List<UserOrderType> = emptyList()
        `when`(
            userRepository.searchUsers(
                queryFilter = queryFilter,
                paging = paging,
                orderTypes = orderTypes
            )
        ).thenReturn(dummyUsers)
        `when`(userRepository.searchUsersCount(queryFilter = queryFilter)).thenReturn(dummyUsers.size.toLong())
        `when`(userConverter.toResponseInBatch(users = dummyUsers)).thenReturn(dummyUserResponses)

        // when
        val result: ApiPagingResponse = userService.getUsers(
            queryFilter = queryFilter,
            paging = paging,
            orderTypes = orderTypes
        )

        // then
        assertThat(result.page).isEqualTo(paging.page)
        assertThat(result.size).isEqualTo(paging.size)
        assertThat(result.data).isEqualTo(dummyUserResponses)
        assertThat(result.totalCount).isEqualTo(dummyUserResponses.size.toLong())
    }

    @Test
    fun `회원 조회`() {
        // given
        val dummyUserId: String = dummyUser.id!!
        `when`(userRepository.findById(id = dummyUserId)).thenReturn(dummyUser)
        `when`(userConverter.toResponse(user = dummyUser)).thenReturn(dummyUserResponse)

        // when
        val result: UserResponse = userService.getUser(id = dummyUserId)

        // then
        assertThat(result).isEqualTo(dummyUserResponse)
    }

    @Test
    fun `존재하지 않는 유저 조회 시 예외 발생`() {
        // given
        val invalidUserId = "invalid-user-id"
        `when`(userRepository.findById(id = invalidUserId)).thenThrow(
            DataNotFoundException(
                code = ErrorCode.USER_NOT_FOUND,
                message = ErrorCode.USER_NOT_FOUND.koreanMessage
            )
        )

        // when & then
        assertThatThrownBy { userService.getUser(id = invalidUserId) }
            .isInstanceOf(DataNotFoundException::class.java)
            .hasMessage(ErrorCode.USER_NOT_FOUND.koreanMessage)
    }
}
