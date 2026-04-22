package com.plink.user.application.dto

import com.plink.user.domain.model.UserSignUpType
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class LoginSocialUserRequest(
    @field:NotEmpty
    val email: String,
    @field:NotEmpty
    val socialId: String,
    @field:NotNull
    val signUpType: UserSignUpType
)
