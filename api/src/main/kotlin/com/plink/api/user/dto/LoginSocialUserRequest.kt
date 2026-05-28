package com.plink.api.user.dto

import com.plink.core.user.vo.UserSignUpType
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
