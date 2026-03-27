package com.plink.user.application

import com.plink.core.dto.ApiPagingResponse
import com.plink.core.dto.Paging
import com.plink.user.application.dto.CreateUserRequest
import com.plink.user.application.dto.UpdateUserRequest
import com.plink.user.application.dto.UserResponse
import com.plink.user.domain.model.User
import com.plink.user.domain.model.UserOrderType
import com.plink.user.domain.repository.UserRepository
import com.plink.user.domain.service.UserConverter
import com.plink.user.domain.service.UserUpdater
import com.plink.user.domain.service.UserValidator
import com.plink.user.infrastructure.persistence.UserQueryFilter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userValidator: UserValidator,
    private val userRepository: UserRepository,
    private val userConverter: UserConverter,
    private val userUpdater: UserUpdater
) {

    @Transactional
    fun createUser(request: CreateUserRequest): String {
        userValidator.validate(request = request)
        val baseUser: User = userConverter.toEntity(request = request)
        return userRepository.save(user = baseUser).id!!
    }

    @Transactional(readOnly = true)
    fun getUser(userId: String): UserResponse {
        val user = userRepository.findById(id = userId)
        return userConverter.toResponse(user)
    }

    @Transactional(readOnly = true)
    fun getUsers(
        queryFilter: UserQueryFilter,
        paging: Paging,
        orderTypes: List<UserOrderType>
    ): ApiPagingResponse {
        val users: List<User> = userRepository.searchUsers(
            queryFilter = queryFilter,
            paging = paging,
            orderTypes = orderTypes
        )
        val totalCount: Long = userRepository.searchUsersCount(queryFilter = queryFilter)
        return ApiPagingResponse.of(
            data = userConverter.toResponseInBatch(users = users),
            totalCount = totalCount,
            page = paging.page,
            size = paging.size,
        )
    }

    @Transactional
    fun updateUser(userId: String, request: UpdateUserRequest): String {
        val user: User = userRepository.findById(id = userId)
        userUpdater.markAsUpdate(
            request = request,
            user = user
        )
        return userId
    }

    @Transactional
    fun resignUser(userId: String): String {
        val user = userRepository.findById(id = userId)
        user.resign()
        return userId
    }
}
