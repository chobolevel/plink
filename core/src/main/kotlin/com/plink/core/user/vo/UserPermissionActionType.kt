package com.plink.core.user.vo

enum class UserPermissionActionType(val code: String) {
    READ("READ"),
    WRITE("WRITE"),
    MANAGE("MANAGE");

    companion object {
        fun fromCode(code: String): UserPermissionActionType {
            return entries.first { it.code == code }
        }
    }
}
