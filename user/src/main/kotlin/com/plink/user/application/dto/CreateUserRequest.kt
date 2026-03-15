package com.plink.user.application.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class CreateUserRequest(
    @field:NotEmpty
    @field:Email
    val email: String,
    @field:NotEmpty
    val password: String,
    @field:NotEmpty
    val nickname: String
)
