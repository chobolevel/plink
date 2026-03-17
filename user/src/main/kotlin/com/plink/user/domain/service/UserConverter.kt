package com.plink.user.domain.service

import com.plink.core.extension.toMillis
import com.plink.user.application.dto.CreateUserRequest
import com.plink.user.application.dto.UserResponse
import com.plink.user.domain.model.User
import com.plink.user.domain.model.UserRoleType
import com.plink.user.domain.model.UserSignUpType
import org.springframework.stereotype.Component

@Component
class UserConverter(
    private val passwordEncoder: UserPasswordEncoder
) {

    fun toEntity(request: CreateUserRequest): User {
        return User(
            email = request.email,
            password = passwordEncoder.encode(rawPassword = request.password),
            signUpType = UserSignUpType.COMMON,
            nickname = request.nickname,
            role = UserRoleType.USER,
            balance = 0
        )
    }

    fun toResponse(user: User): UserResponse {
        return UserResponse(
            id = user.id!!,
            email = user.email,
            signUpType = user.signUpType,
            signUpTypeName = user.signUpType.korean,
            nickname = user.nickname,
            role = user.role,
            balance = user.balance,
            createdAt = user.createdAt!!.toMillis(),
            updatedAt = user.updatedAt!!.toMillis()
        )
    }

    fun toResponseInBatch(users: List<User>): List<UserResponse> {
        return users.map { toResponse(it) }
    }
}
