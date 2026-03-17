package com.plink.api.controller.v1

import com.plink.core.dto.ApiPagingResponse
import com.plink.core.dto.ApiResponse
import com.plink.core.dto.Paging
import com.plink.core.dto.PagingRequest
import com.plink.user.application.UserService
import com.plink.user.application.dto.CreateUserRequest
import com.plink.user.application.dto.SearchUserRequest
import com.plink.user.infrastructure.persistence.UserQueryFilter
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/users")
    fun createUser(
        @Valid @RequestBody
        request: CreateUserRequest
    ): ResponseEntity<ApiResponse> {
        val result: String = userService.createUser(request = request)
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @GetMapping("/users")
    fun getUsers(
        searchRequest: SearchUserRequest,
        pagingRequest: PagingRequest
    ): ResponseEntity<ApiPagingResponse> {
        val queryFilter = UserQueryFilter(
            email = searchRequest.email,
            signUpType = searchRequest.signUpType,
            nickname = searchRequest.nickname,
            role = searchRequest.role,
            isResigned = searchRequest.isResigned
        )
        val paging = Paging(
            page = pagingRequest.page ?: 1,
            size = pagingRequest.size ?: 20
        )
        val result: ApiPagingResponse = userService.getUsers(
            queryFilter = queryFilter,
            paging = paging,
            orderTypes = searchRequest.orderTypes ?: emptyList()
        )
        return ResponseEntity.ok(result)
    }
}
