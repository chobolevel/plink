package com.plink.core.user.service

import com.plink.core.user.entity.UserPermission
import com.plink.core.user.vo.UserRoleType
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
