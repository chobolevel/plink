package com.plink.api.user.dto

import com.plink.core.user.vo.UserUpdateMask
import jakarta.validation.constraints.Size

data class UpdateUserRequest(
    val nickname: String?,
    @field:Size(min = 1)
    val updateMask: List<UserUpdateMask>
)
