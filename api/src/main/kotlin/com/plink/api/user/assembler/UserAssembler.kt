package com.plink.api.user.assembler

import com.plink.core.user.entity.User
import com.plink.core.user.entity.UserPermission
import org.springframework.stereotype.Component

@Component
class UserAssembler {

    fun assemble(user: User, userPermissions: List<UserPermission>): User {
        user.addUserPermissions(userPermissions = userPermissions)
        return user
    }
}
