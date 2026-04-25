package com.plink.api.user.application.updater

import com.plink.api.user.application.dto.UpdateUserRequest
import com.plink.core.user.domain.model.User
import com.plink.core.user.domain.model.UserUpdateMask
import org.springframework.stereotype.Component

@Component
class UserUpdater {

    fun markAsUpdate(request: UpdateUserRequest, user: User): User {
        request.updateMask.forEach {
            when (it) {
                UserUpdateMask.NICKNAME -> user.nickname = request.nickname!!
            }
        }
        return user
    }
}
