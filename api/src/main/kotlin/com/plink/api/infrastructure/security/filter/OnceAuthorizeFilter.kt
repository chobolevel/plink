package com.plink.api.infrastructure.security.filter

import com.plink.core.infrastructure.security.TokenProvider
import com.plink.user.domain.model.User
import com.plink.user.domain.repository.UserRepository
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

        if (token != null && tokenProvider.validateToken(token = token)) {
            val userId: String = tokenProvider.getUserId(token = token)
            val user: User = userRepository.findById(id = userId)
            val authentication: UsernamePasswordAuthenticationToken = getAuthentication(user = user)
            SecurityContextHolder.getContext().authentication = authentication
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
        return UsernamePasswordAuthenticationToken(
            user.id!!,
            null,
            AuthorityUtils.createAuthorityList(user.role.code)
        ).also { it.details = user }
    }
}
