package com.plink.user

import com.plink.api.user.application.dto.AddUserPermissionRequest
import com.plink.core.user.domain.model.UserPermission
import com.plink.core.user.domain.model.UserPermissionActionType
import com.plink.core.user.domain.model.UserPermissionResourceType

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

    private val dummyAddUserPermissionRequest: AddUserPermissionRequest by lazy {
        AddUserPermissionRequest(
            resource = resource,
            action = action
        )
    }

    fun toEntity(): UserPermission = dummyUserPermission

    fun toAddRequest(): AddUserPermissionRequest = dummyAddUserPermissionRequest
}
