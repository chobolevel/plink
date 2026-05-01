package com.plink.api.user.application.converter

import com.plink.api.user.application.dto.AddUserPermissionRequest
import com.plink.core.user.domain.model.UserPermission
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
