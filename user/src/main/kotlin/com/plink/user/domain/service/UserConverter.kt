package com.plink.user.domain.service

import com.plink.user.application.dto.CreateUserRequest
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
}
