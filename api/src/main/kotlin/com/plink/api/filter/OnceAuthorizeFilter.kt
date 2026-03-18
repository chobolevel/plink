package com.plink.api.filter

import com.plink.api.provider.TokenProvider
import com.plink.user.domain.repository.UserRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class OnceAuthorizeFilter(
    private val tokenProvider: TokenProvider,
    private val userRepository: UserRepository
) : OncePerRequestFilter() {

    private val accessTokenPrefix: String = "Bearer "

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeaderValue: String? = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authorizationHeaderValue == null || !authorizationHeaderValue.startsWith(accessTokenPrefix)) {
            filterChain.doFilter(request, response)
            return
        }
        val accessToken: String = authorizationHeaderValue.substring(accessTokenPrefix.length)
        tokenProvider.validateToken(token = accessToken)
        val userId: String = tokenProvider.getId(token = accessToken)
        userRepository.findById(id = userId).also { user ->
            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                user.id!!,
                null,
                AuthorityUtils.createAuthorityList(user.role.name)
            ).also {
                it.details = user
            }
        }
        filterChain.doFilter(request, response)
    }
}
