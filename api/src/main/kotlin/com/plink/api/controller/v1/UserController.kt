package com.plink.api.controller.v1

import com.plink.api.annotation.UserAuthorize
import com.plink.core.dto.ApiResponse
import com.plink.core.extension.getUserId
import com.plink.user.application.UserService
import com.plink.user.application.dto.CreateUserRequest
import com.plink.user.application.dto.UpdateUserRequest
import com.plink.user.application.dto.UserResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

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

    @UserAuthorize
    @GetMapping("/user/me")
    fun getMe(principal: Principal): ResponseEntity<ApiResponse> {
        val result: UserResponse = userService.getUser(userId = principal.getUserId())
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @UserAuthorize
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

    @UserAuthorize
    @PostMapping("/user/resign")
    fun resign(principal: Principal): ResponseEntity<ApiResponse> {
        val result: String = userService.resignUser(userId = principal.getUserId())
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }
}
