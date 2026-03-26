package com.plink.api.controller.v1

import com.plink.core.dto.ApiResponse
import com.plink.core.dto.JwtResponse
import com.plink.user.application.AuthService
import com.plink.user.application.dto.LoginCommonUserRequest
import com.plink.user.application.dto.LoginSocialUserRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth(인증)", description = "인증 도메인 API")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    @Operation(summary = "일반 회원 로그인 API")
    @PostMapping("/login/user")
    fun loginUser(
        @Valid @RequestBody
        request: LoginCommonUserRequest
    ): ResponseEntity<ApiResponse> {
        val result: JwtResponse = authService.loginUser(request = request)
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @Operation(summary = "소셜 회원 로그인 API")
    @PostMapping("/login/social-user")
    fun loginSocialUser(
        @Valid @RequestBody
        request: LoginSocialUserRequest
    ): ResponseEntity<ApiResponse> {
        val result: JwtResponse = authService.loginSocialUser(request = request)
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }

    @Operation(summary = "토큰 갱신 API")
    @PostMapping("/reissue")
    fun reissue(@RequestParam("refreshToken") refreshToken: String): ResponseEntity<ApiResponse> {
        val result: JwtResponse = authService.reissue(refreshToken)
        return ResponseEntity.ok(ApiResponse.of(data = result))
    }
}
