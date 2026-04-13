package com.plink.user.domain.model

enum class UserRoleType(val code: String) {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    companion object {
        fun fromCode(code: String) = entries.first { it.code == code }
    }
}
