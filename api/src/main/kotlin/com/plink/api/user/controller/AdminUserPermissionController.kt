package com.plink.api.user.controller

import com.plink.api.user.dto.AddUserPermissionRequest
import com.plink.api.user.service.UserPermissionService
import com.plink.core.common.dto.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "UserPermission(회원 권한)", description = "회원 권한 관리 API")
@RestController
@RequestMapping("/api/admin/v1/users")
class AdminUserPermissionController(
    private val userPermissionService: UserPermissionService,
) {

    @Operation(summary = "회원 권한 추가")
    @PreAuthorize("hasAuthority('USER:MANAGE')")
    @PostMapping("/{userId}/permissions")
    fun addUserPermission(
        @PathVariable userId: String,
        @Valid @RequestBody
        request: AddUserPermissionRequest
    ): ResponseEntity<ApiResponse> {
        val result: Boolean = userPermissionService.addUserPermission(
            userId = userId,
            request = request
        )
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @Operation(summary = "회원 권한 제거")
    @PreAuthorize("hasAuthority('USER:MANAGE')")
    @DeleteMapping("/{userId}/permissions/{userPermissionId}")
    fun subUserPermission(
        @PathVariable userId: String,
        @PathVariable userPermissionId: String,
    ): ResponseEntity<ApiResponse> {
        val result: Boolean = userPermissionService.subUserPermission(
            userId = userId,
            userPermissionId = userPermissionId
        )
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }
}
