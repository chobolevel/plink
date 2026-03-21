plugins {
    kotlin("jvm")
    kotlin("plugin.spring") // @Service, @Transactional 등을 위해 필수
    kotlin("plugin.jpa") // @Entity 등을 위해 필수
    kotlin("kapt")
    id("org.springframework.boot") // 라이브러리 구체적인 버전 결정
    id("io.spring.dependency-management") // 의존성 관리
}

dependencies {
    api("org.springframework.data:spring-data-envers")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-validation")
    api("com.github.f4b6a3:tsid-creator:5.2.6")
    api("org.springframework.boot:spring-boot-starter-data-redis")

    // query dsl
    val queryDslVersion = "5.0.0"
    api("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
    kapt("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")

    // jwt
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks {
    bootJar { enabled = false }
    jar { enabled = true }
}
