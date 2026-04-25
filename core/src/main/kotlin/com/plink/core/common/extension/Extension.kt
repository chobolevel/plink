package com.plink.core.common.extension

import java.security.Principal
import java.time.OffsetDateTime

fun Principal.getUserId(): String {
    return this.name
}

fun OffsetDateTime.toMillis(): Long {
    return this.toInstant().toEpochMilli()
}
