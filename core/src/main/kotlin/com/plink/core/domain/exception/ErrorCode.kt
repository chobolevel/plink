package com.plink.core.domain.exception

enum class ErrorCode(val koreanMessage: String) {
    // COMMON
    FORBIDDEN("접근 권한이 없습니다."),
    BAD_CREDENTIAL("아이디 또는 비밀번호가 일치하지 않습니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("만료된 토큰입니다."),
    INVALID_PARAMETER("파라미터가 유효하지 않습니다."),

    // USER
    ALREADY_EXISTS_EMAIL("이미 존재하는 이메일입니다."),
    USER_NOT_FOUND("회원을 찾을 수 없습니다.")
}
