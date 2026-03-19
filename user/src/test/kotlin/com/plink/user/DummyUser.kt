package com.plink.user

import com.plink.user.application.dto.CreateUserRequest
import com.plink.user.application.dto.LoginCommonUserRequest
import com.plink.user.application.dto.UpdateUserRequest
import com.plink.user.application.dto.UserResponse
import com.plink.user.domain.model.User
import com.plink.user.domain.model.UserRoleType
import com.plink.user.domain.model.UserSignUpType
import com.plink.user.domain.model.UserUpdateMask

object DummyUser {
    private const val id: String = "test-user-id"
    private const val email: String = "user@naver.com"
    private const val password: String = "password"
    private val signUpType: UserSignUpType = UserSignUpType.COMMON
    private const val nickname: String = "testUser"
    private val role: UserRoleType = UserRoleType.USER
    private const val balance: Int = 0
    private const val createdAt: Long = 0L
    private const val updatedAt: Long = 0L

    private val dummyUser: User by lazy {
        User(
            email = email,
            password = password,
            socialId = null,
            signUpType = signUpType,
            nickname = nickname,
            role = role,
            balance = balance
        ).also { it.id = id }
    }

    private val dummyUserResponse: UserResponse by lazy {
        UserResponse(
            id = id,
            email = email,
            signUpType = signUpType,
            signUpTypeName = signUpType.korean,
            nickname = nickname,
            role = role,
            balance = balance,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    private val dummyCreateRequest: CreateUserRequest by lazy {
        CreateUserRequest(
            email = email,
            password = password,
            nickname = nickname,
        )
    }

    private val dummyUpdateRequest: UpdateUserRequest by lazy {
        UpdateUserRequest(
            nickname = "chobo",
            updateMask = listOf(UserUpdateMask.NICKNAME)
        )
    }

    private val dummyLoginCommonUserRequest: LoginCommonUserRequest by lazy {
        LoginCommonUserRequest(
            email = email,
            password = password
        )
    }

    fun toEntity(): User = dummyUser

    fun toResponse(): UserResponse = dummyUserResponse

    fun toCreateRequest(): CreateUserRequest = dummyCreateRequest

    fun toUpdateRequest(): UpdateUserRequest = dummyUpdateRequest

    fun toLoginCommonUserRequest(): LoginCommonUserRequest = dummyLoginCommonUserRequest
}
