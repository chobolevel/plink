package com.plink.api.user.updater

import com.plink.api.user.dto.UpdateUserRequest
import com.plink.core.user.entity.User
import com.plink.core.user.vo.UserUpdateMask
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
