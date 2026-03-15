package com.plink.user.application

import com.plink.user.application.dto.CreateUserRequest
import com.plink.user.domain.model.User
import com.plink.user.domain.repository.UserRepository
import com.plink.user.domain.service.UserConverter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userConverter: UserConverter
) {

    @Transactional
    fun createUser(request: CreateUserRequest): String {
        val baseUser: User = userConverter.toEntity(request = request)
        return userRepository.save(user = baseUser).id!!
    }
}
