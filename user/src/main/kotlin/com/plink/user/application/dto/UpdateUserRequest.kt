package com.plink.user.application.dto

import com.plink.user.domain.model.UserUpdateMask
import jakarta.validation.constraints.Size

data class UpdateUserRequest(
    val nickname: String?,
    @field:Size(min = 1)
    val updateMask: List<UserUpdateMask>
)
