package com.plink.core.extension

import java.security.Principal
import java.time.OffsetDateTime

fun Principal.getUserId(): Long {
    return this.name.toLong()
}

fun OffsetDateTime.toMillis(): Long {
    return this.toInstant().toEpochMilli()
}
