package com.plink.api.common.support

interface PasswordSupporter {

    fun encode(password: String): String

    fun matches(password: String, encodedPassword: String): Boolean
}
