package com.plink.api.user.dto

import com.plink.core.user.vo.UserPermissionActionType
import com.plink.core.user.vo.UserPermissionResourceType
import jakarta.validation.constraints.NotNull

data class AddUserPermissionRequest(
    @field:NotNull
    val resource: UserPermissionResourceType,
    @field:NotNull
    val action: UserPermissionActionType
)
