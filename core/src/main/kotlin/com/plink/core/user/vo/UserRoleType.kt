package com.plink.core.user.vo

enum class UserRoleType(val code: String) {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    companion object {
        fun fromCode(code: String) = entries.first { it.code == code }
    }
}
