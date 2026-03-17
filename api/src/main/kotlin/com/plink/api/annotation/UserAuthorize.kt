package com.plink.api.annotation

import org.springframework.security.access.prepost.PreAuthorize

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@PreAuthorize("hasRole('USER')")
annotation class UserAuthorize
