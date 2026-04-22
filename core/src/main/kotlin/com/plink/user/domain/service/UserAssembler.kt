package com.plink.user.domain.service

import com.plink.user.domain.model.User
import com.plink.user.domain.model.UserPermission
import org.springframework.stereotype.Component

@Component
class UserAssembler {

    fun assemble(user: User, userPermissions: List<UserPermission>): User {
        user.addUserPermissions(userPermissions = userPermissions)
        return user
    }
}
