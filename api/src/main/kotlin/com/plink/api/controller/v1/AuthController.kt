package com.plink.api.controller.v1

import com.plink.core.dto.ApiResponse
import com.plink.core.dto.JwtResponse
import com.plink.user.application.AuthService
import com.plink.user.application.dto.LoginCommonUserRequest
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth(인증)", description = "인증 도메인 API")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login/user")
    fun loginUser(
        @Valid @RequestBody
        request: LoginCommonUserRequest
    ): ResponseEntity<ApiResponse> {
        val result: JwtResponse = authService.loginUser(request = request)
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }
}
