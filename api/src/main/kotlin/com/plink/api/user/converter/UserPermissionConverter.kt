package com.plink.api.user.converter

import com.plink.api.user.dto.AddUserPermissionRequest
import com.plink.core.user.entity.UserPermission
import org.springframework.stereotype.Component

@Component
class UserPermissionConverter {

    fun toEntity(request: AddUserPermissionRequest): UserPermission {
        return UserPermission(
            resource = request.resource,
            action = request.action
        )
    }
}
