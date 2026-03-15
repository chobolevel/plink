package com.plink.user

import com.plink.user.application.UserService
import com.plink.user.application.dto.CreateUserRequest
import com.plink.user.domain.model.User
import com.plink.user.domain.repository.UserRepository
import com.plink.user.domain.service.UserConverter
import org.assertj.core.api.Assertions.assertThat
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
}
