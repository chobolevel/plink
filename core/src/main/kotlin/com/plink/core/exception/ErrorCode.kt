package com.plink.core.exception

enum class ErrorCode(val koreanMessage: String) {
    // COMMON
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("만료된 토큰입니다."),

    // USER
    USER_NOT_FOUND("회원을 찾을 수 없습니다.")
}
