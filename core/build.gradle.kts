plugins {
    id("org.springframework.boot") // 라이브러리 구체적인 버전 결정
    id("io.spring.dependency-management") // 의존성 관리
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-validation")
}

tasks {
    bootJar { enabled = false }
    jar { enabled = true }
}
