package com.plink.core.user.domain.service

import com.plink.core.user.domain.model.User
import com.plink.core.user.domain.model.UserPermission
import org.springframework.stereotype.Component

@Component
class UserAssembler {

    fun assemble(user: User, userPermissions: List<UserPermission>): User {
        user.addUserPermissions(userPermissions = userPermissions)
        return user
    }
}
