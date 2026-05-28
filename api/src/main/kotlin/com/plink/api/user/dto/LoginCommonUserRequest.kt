package com.plink.api.user.dto

data class LoginCommonUserRequest(
    val email: String,
    val password: String,
)
