package com.plink.api.user.application.dto

data class LoginCommonUserRequest(
    val email: String,
    val password: String,
)
