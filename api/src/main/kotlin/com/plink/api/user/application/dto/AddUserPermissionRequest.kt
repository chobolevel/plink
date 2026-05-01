package com.plink.api.user.application.dto

import com.plink.core.user.domain.model.UserPermissionActionType
import com.plink.core.user.domain.model.UserPermissionResourceType
import jakarta.validation.constraints.NotNull

data class AddUserPermissionRequest(
    @field:NotNull
    val resource: UserPermissionResourceType,
    @field:NotNull
    val action: UserPermissionActionType
)
