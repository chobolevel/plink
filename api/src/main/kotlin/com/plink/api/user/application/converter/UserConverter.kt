package com.plink.api.user.application.converter

import com.plink.api.user.application.dto.CreateSocialUserRequest
import com.plink.api.user.application.dto.CreateUserRequest
import com.plink.api.user.application.dto.UserResponse
import com.plink.core.common.extension.toMillis
import com.plink.core.user.domain.model.User
import com.plink.core.user.domain.model.UserRoleType
import com.plink.core.user.domain.model.UserSignUpType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserConverter(
    private val passwordEncoder: BCryptPasswordEncoder
) {

    fun toEntity(request: CreateUserRequest): User {
        return User(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            signUpType = UserSignUpType.COMMON,
            nickname = request.nickname,
            role = UserRoleType.USER,
            balance = 0
        )
    }

    fun toEntity(request: CreateSocialUserRequest): User {
        return User(
            email = request.email,
            password = null,
            socialId = request.socialId,
            signUpType = request.signUpType,
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
