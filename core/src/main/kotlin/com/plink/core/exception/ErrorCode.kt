package com.plink.core.exception

enum class ErrorCode(val koreanMessage: String) {
    // COMMON
    BAD_CREDENTIAL("아이디 또는 비밀번호가 일치하지 않습니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("만료된 토큰입니다."),

    // USER
    USER_NOT_FOUND("회원을 찾을 수 없습니다.")
}
