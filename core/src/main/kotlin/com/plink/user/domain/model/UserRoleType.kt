package com.plink.user.domain.model

enum class UserRoleType(val code: String, val defaultPermissions: Map<UserPermissionResourceType, List<UserPermissionActionType>>) {
    ADMIN(
        "ROLE_ADMIN",
        mapOf(
            UserPermissionResourceType.USER to listOf(UserPermissionActionType.READ, UserPermissionActionType.WRITE, UserPermissionActionType.MANAGE),
            UserPermissionResourceType.POST to listOf(UserPermissionActionType.READ, UserPermissionActionType.WRITE, UserPermissionActionType.MANAGE),
        )
    ),
    USER(
        "ROLE_USER",
        mapOf(
            UserPermissionResourceType.USER to listOf(UserPermissionActionType.READ, UserPermissionActionType.WRITE),
            UserPermissionResourceType.POST to listOf(UserPermissionActionType.READ, UserPermissionActionType.WRITE),
        )
    );

    companion object {
        fun fromCode(code: String) = entries.first { it.code == code }
    }
}
