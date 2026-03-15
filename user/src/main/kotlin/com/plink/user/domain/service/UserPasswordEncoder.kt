package com.plink.user.domain.service

interface UserPasswordEncoder {

    fun encode(rawPassword: String): String
}
