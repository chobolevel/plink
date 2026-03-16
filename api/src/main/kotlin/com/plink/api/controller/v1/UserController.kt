package com.plink.api.controller.v1

import com.plink.core.dto.ApiResponse
import com.plink.user.application.UserService
import com.plink.user.application.dto.CreateUserRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
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
}
