package com.plink.user.domain.service

import com.plink.user.domain.model.UserPermission
import com.plink.user.domain.model.UserRoleType
import org.springframework.stereotype.Component

@Component
class UserPermissionGenerator {

    fun generateUserPermissions(role: UserRoleType): List<UserPermission> {
        return role.defaultPermissions.map { record ->
            record.value.map { action ->
                UserPermission(
                    resource = record.key,
                    action = action
                )
            }
        }.flatten()
    }
}
