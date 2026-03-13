plugins {
    id("org.springframework.boot") // 라이브러리 구체적인 버전 결정
    id("io.spring.dependency-management") // 의존성 관리
    kotlin("jvm")
    kotlin("plugin.spring") // @Service, @Transactional 등을 위해 필수
    kotlin("plugin.jpa") // @Entity 등을 위해 필수
}

dependencies {
    implementation(project(":user"))
    implementation("org.springframework.boot:spring-boot-starter-web")

    runtimeOnly("com.mysql:mysql-connector-j")
}
