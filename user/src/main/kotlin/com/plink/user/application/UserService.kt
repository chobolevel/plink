package com.plink.user.application

import com.plink.core.dto.ApiPagingResponse
import com.plink.core.dto.Paging
import com.plink.user.application.dto.CreateUserRequest
import com.plink.user.application.dto.UserResponse
import com.plink.user.domain.model.User
import com.plink.user.domain.model.UserOrderType
import com.plink.user.domain.repository.UserRepository
import com.plink.user.domain.service.UserConverter
import com.plink.user.infrastructure.persistence.UserQueryFilter
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

    @Transactional(readOnly = true)
    fun getUser(id: String): UserResponse {
        val user = userRepository.findById(id)
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
}
