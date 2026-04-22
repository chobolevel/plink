package com.plink.user.domain.model

enum class UserPermissionResourceType(val code: String) {
    USER("USER"),
    POST("POST");

    companion object {
        fun fromCode(code: String): UserPermissionResourceType {
            return entries.first { it.code == code }
        }
    }
}
