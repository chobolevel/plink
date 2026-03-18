package com.plink.user.application.dto

import com.plink.user.domain.model.UserUpdateMask
import jakarta.validation.constraints.Size

data class UpdateUserRequest(
    val nickname: String?,
    @field:Size(min = 1)
    val updateMask: List<UserUpdateMask>
) {
    init {
        if (UserUpdateMask.NICKNAME in updateMask) {
            require(!nickname.isNullOrEmpty()) { "nickname은(는) 필수 값입니다." }
        }
    }
}
