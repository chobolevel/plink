package com.plink.api.common.filter

import com.plink.core.common.security.TokenProvider
import com.plink.core.user.entity.User
import com.plink.core.user.repository.UserRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class OnceAuthorizeFilter(
    private val tokenProvider: TokenProvider,
    private val userRepository: UserRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token: String? = resolveToken(request = request)

        if (token != null) {
            runCatching { tokenProvider.validateToken(token = token) }
                .onSuccess {
                    val userId: String = tokenProvider.getUserId(token = token)
                    // DB 조회하면 stateless 장점이 없음 굳이 조회가 필요하다면 캐싱
                    val user: User = userRepository.findById(id = userId)
                    SecurityContextHolder.getContext().authentication = getAuthentication(user = user)
                }
        }

        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }

    private fun getAuthentication(user: User): UsernamePasswordAuthenticationToken {
        val role: String = user.role.code
        return UsernamePasswordAuthenticationToken(
            user.id!!,
            null,
            AuthorityUtils.createAuthorityList(role)
        ).also { it.details = user }
    }
}
