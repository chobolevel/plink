package com.plink.user.application.dto

import com.plink.user.domain.model.UserSignUpType
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class CreateSocialUserRequest(
    @field:NotEmpty(message = "이메일은 필수 값입니다.")
    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    val email: String,

    @field:NotEmpty(message = "닉네임은 필수 값입니다.")
    @field:Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,20}$", message = "닉네임 형식이 올바르지 않습니다.")
    val nickname: String,

    @field:NotEmpty(message = "소셜 아이디는 필수 값입니다.")
    val socialId: String,

    @field:NotNull(message = "가입 유형은 필수 값입니다.")
    val signUpType: UserSignUpType
)
