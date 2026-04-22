package com.plink.user

import com.plink.user.domain.model.UserPermission
import com.plink.user.domain.model.UserPermissionActionType
import com.plink.user.domain.model.UserPermissionResourceType

object DummyUserPermission {
    private const val id = "user-permission-id"
    private const val userId = "user-id"
    private val resource: UserPermissionResourceType = UserPermissionResourceType.USER
    private val action: UserPermissionActionType = UserPermissionActionType.READ
    private const val createdAt: Long = 0L
    private const val updatedAt: Long = 0L

    private val dummyUserPermission: UserPermission by lazy {
        UserPermission(
            resource = resource,
            action = action,
        ).also { it.id = id }
    }

    fun toEntity(): UserPermission = dummyUserPermission
}
