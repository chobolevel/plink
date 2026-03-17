package com.plink.user

import com.plink.core.dto.ApiPagingResponse
import com.plink.core.dto.Paging
import com.plink.user.application.UserService
import com.plink.user.application.dto.CreateUserRequest
import com.plink.user.application.dto.UserResponse
import com.plink.user.domain.model.User
import com.plink.user.domain.model.UserOrderType
import com.plink.user.domain.repository.UserRepository
import com.plink.user.domain.service.UserConverter
import com.plink.user.infrastructure.persistence.UserQueryFilter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.aot.hint.TypeReference.listOf

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
    fun createUserTest() {
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
    fun getUsersTest() {
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
}
