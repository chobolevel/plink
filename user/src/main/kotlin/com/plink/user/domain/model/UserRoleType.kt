package com.plink.user.domain.model

enum class UserRoleType(val code: String) {
    USER("USER"),
    ADMIN("ADMIN");

    companion object {
        fun fromCode(code: String) = entries.first { it.code == code }
    }
}
