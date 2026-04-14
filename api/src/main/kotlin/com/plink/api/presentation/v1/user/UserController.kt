package com.plink.api.presentation.v1.user

import com.plink.api.common.annotation.AnyAuthorize
import com.plink.core.extension.getUserId
import com.plink.core.presentation.dto.ApiResponse
import com.plink.user.application.UserService
import com.plink.user.application.dto.CreateSocialUserRequest
import com.plink.user.application.dto.CreateUserRequest
import com.plink.user.application.dto.UpdateUserRequest
import com.plink.user.application.dto.UserResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "User(회원)", description = "회원 도메인 API")
@RestController
@RequestMapping("/api/v1")
class UserController(
    private val userService: UserService
) {

    @Operation(summary = "일반 회원 가입 API")
    @PostMapping("/users")
    fun createUser(
        @Valid @RequestBody
        request: CreateUserRequest
    ): ResponseEntity<ApiResponse> {
        val result: String = userService.createUser(request = request)
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @Operation(summary = "소셜 회원 가입 API")
    @PostMapping("/social-users")
    fun createSocialUser(
        @Valid @RequestBody
        request: CreateSocialUserRequest
    ): ResponseEntity<ApiResponse> {
        val result: String = userService.createSocialUser(request = request)
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @Operation(summary = "회원 정보 조회 API(본인)")
    @AnyAuthorize
    @GetMapping("/user/me")
    fun getMe(principal: Principal): ResponseEntity<ApiResponse> {
        val result: UserResponse = userService.getUser(userId = principal.getUserId())
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @Operation(summary = "회원 정보 수정 API(본인)")
    @AnyAuthorize
    @PatchMapping("/user/me")
    fun updateMe(
        principal: Principal,
        @Valid @RequestBody
        request: UpdateUserRequest
    ): ResponseEntity<ApiResponse> {
        val result: String = userService.updateUser(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @Operation(summary = "회원 탈퇴 API(본인)")
    @AnyAuthorize
    @PostMapping("/user/me/resign")
    fun resign(principal: Principal): ResponseEntity<ApiResponse> {
        val result: String = userService.resignUser(userId = principal.getUserId())
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }
}
