package com.plink.api.user.converter

import com.plink.api.common.support.PasswordSupporter
import com.plink.api.user.dto.CreateSocialUserRequest
import com.plink.api.user.dto.CreateUserRequest
import com.plink.api.user.dto.UserResponse
import com.plink.core.common.extension.toMillis
import com.plink.core.user.entity.User
import com.plink.core.user.vo.UserRoleType
import com.plink.core.user.vo.UserSignUpType
import org.springframework.stereotype.Component

@Component
class UserConverter(
    private val passwordSupporter: PasswordSupporter
) {

    fun toEntity(request: CreateUserRequest): User {
        return User(
            email = request.email,
            password = passwordSupporter.encode(request.password),
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
