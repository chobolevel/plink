package com.plink.core.common.exception

enum class ErrorCode(val koreanMessage: String) {
    // COMMON
    FORBIDDEN("접근 권한이 없습니다."),
    BAD_CREDENTIAL("아이디 또는 비밀번호가 일치하지 않습니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("만료된 토큰입니다."),
    INVALID_PARAMETER("파라미터가 유효하지 않습니다."),
    INTERNAL_SERVER_ERROR("내부 서버에서 에러가 발생하였습니다."),

    // POST
    POST_NOT_FOUND("게시글을 찾을 수 없습니다."),

    // POST COMMENT
    POST_COMMENT_NOT_FOUND("게시글 댓글을 찾을 수 없습니다."),

    // USER
    ALREADY_EXISTS_EMAIL("이미 존재하는 이메일입니다."),
    USER_NOT_FOUND("회원을 찾을 수 없습니다."),

    // REGION
    REGION_NOT_FOUND("해당 지역을 찾을 수 없습니다."),
    CIRCULAR_REGION("현재 지역을 부모 지역으로 참조할 수 없습니다.")
}
