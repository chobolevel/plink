package com.plink.api.user.controller

import com.plink.api.user.dto.CreateUserRequest
import com.plink.api.user.dto.SearchUserRequest
import com.plink.api.user.dto.UpdateUserRequest
import com.plink.api.user.dto.UserResponse
import com.plink.api.user.service.UserService
import com.plink.core.common.dto.ApiPagingResponse
import com.plink.core.common.dto.ApiResponse
import com.plink.core.common.dto.Paging
import com.plink.core.common.dto.PagingRequest
import com.plink.core.user.repository.UserQueryFilter
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User(회원)", description = "회원 도메인 API")
@RestController
@RequestMapping("/api/admin/v1")
class AdminUserController(
    private val userService: UserService
) {

    @PreAuthorize("hasAuthority('USER:MANAGE')")
    @Operation(summary = "회원 등록 API")
    @PostMapping("/users")
    fun createUser(
        @Valid @RequestBody
        request: CreateUserRequest
    ): ResponseEntity<ApiResponse> {
        val result: String = userService.createUser(request = request)
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @PreAuthorize("hasAuthority('USER:MANAGE')")
    @Operation(summary = "회원 정보 조회 API")
    @GetMapping("/users/{userId}")
    fun getUser(
        @PathVariable userId: String
    ): ResponseEntity<ApiResponse> {
        val result: UserResponse = userService.getUser(userId = userId)
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @PreAuthorize("hasAuthority('USER:MANAGE')")
    @Operation(summary = "회원 정보 목록 조회 API")
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

    @PreAuthorize("hasAuthority('USER:MANAGE')")
    @Operation(summary = "회원 정보 수정 API")
    @PatchMapping("/users/{userId}")
    fun updateUser(
        @PathVariable userId: String,
        @Valid @RequestBody
        request: UpdateUserRequest
    ): ResponseEntity<ApiResponse> {
        val result: String = userService.updateUser(userId = userId, request = request)
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @PreAuthorize("hasAuthority('USER:MANAGE')")
    @Operation(summary = "회원 탈퇴 API")
    @PostMapping("/users/{userId}/resign")
    fun resignUser(
        @PathVariable userId: String
    ): ResponseEntity<ApiResponse> {
        val result: String = userService.resignUser(userId = userId)
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }
}
