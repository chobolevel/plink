package com.plink.user.domain.model

enum class UserSignUpType(val code: String, val korean: String) {
    COMMON("COMMON", "일반"),
    KAKAO("KAKAO", "카카오"),
    NAVER("NAVER", "네이버"),
    GOOGLE("GOOGLE", "구글");

    companion object {
        fun fromCode(code: String) = entries.first { it.code == code }
    }
}
