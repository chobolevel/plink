package com.plink.api.user.application

import com.plink.api.user.application.converter.UserPermissionConverter
import com.plink.api.user.application.dto.AddUserPermissionRequest
import com.plink.core.user.domain.model.User
import com.plink.core.user.domain.model.UserPermission
import com.plink.core.user.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserPermissionService(
    private val userRepository: UserRepository,
    private val userPermissionConverter: UserPermissionConverter
) {

    @Transactional
    fun addUserPermission(userId: String, request: AddUserPermissionRequest): Boolean {
        val user: User = userRepository.findById(id = userId)
        val userPermission: UserPermission = userPermissionConverter.toEntity(request = request)
        user.addUserPermission(userPermission = userPermission)
        return true
    }

    @Transactional
    fun subUserPermission(userId: String, userPermissionId: String): Boolean {
        val user: User = userRepository.findById(id = userId)
        user.subUserPermissionById(id = userPermissionId)
        return true
    }
}
