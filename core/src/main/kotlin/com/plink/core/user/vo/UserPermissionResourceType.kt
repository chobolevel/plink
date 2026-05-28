package com.plink.core.user.vo

enum class UserPermissionResourceType(val code: String) {
    USER("USER"),
    POST("POST"),
    POST_COMMENT("POST_COMMENT");

    companion object {
        fun fromCode(code: String): UserPermissionResourceType {
            return entries.first { it.code == code }
        }
    }
}
