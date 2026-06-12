package com.plink.api.common.annotation

import org.springframework.security.access.prepost.PreAuthorize

@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasRole('USER', 'ADMIN')")
annotation class Authenticated()
