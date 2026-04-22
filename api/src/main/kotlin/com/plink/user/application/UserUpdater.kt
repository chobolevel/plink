package com.plink.user.application

import com.plink.user.application.dto.UpdateUserRequest
import com.plink.user.domain.model.User
import com.plink.user.domain.model.UserUpdateMask
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
