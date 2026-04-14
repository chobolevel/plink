package com.plink.user.application.dto

import com.plink.core.domain.constant.RegexConstant
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

data class CreateUserRequest(
    @field:NotEmpty(message = "이메일은 필수 값입니다.")
    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    val email: String,
    @field:NotEmpty(message = "비밀번호는 필수 값입니다.")
    // 영어 + 숫자 + 특수문자(!@#$%^&*) 조합 최소 8자리 이상
    @field:Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$", message = "비밀번호 형식이 올바르지 않습니다.")
    val password: String,
    @field:NotEmpty(message = "닉네임은 필수 값입니다.")
    // 한글 + 영어 + 숫자 조합 최소 2자리 이상 20자리 이하
    @field:Pattern(regexp = RegexConstant.NICKNAME_REGEX, message = "닉네임 형식이 올바르지 않습니다.")
    val nickname: String
)
