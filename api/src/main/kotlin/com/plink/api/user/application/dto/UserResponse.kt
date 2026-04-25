package com.plink.api.user.application.dto

import com.plink.core.user.domain.model.UserRoleType
import com.plink.core.user.domain.model.UserSignUpType

data class UserResponse(
    val id: String,
    val email: String,
    val signUpType: UserSignUpType,
    val signUpTypeName: String,
    val nickname: String,
    val role: UserRoleType,
    val balance: Int,
    val createdAt: Long,
    val updatedAt: Long,
)
