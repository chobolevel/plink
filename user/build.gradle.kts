plugins {
    kotlin("jvm")
    kotlin("plugin.spring") // @Service, @Transactional 등을 위해 필수
    kotlin("plugin.jpa") // @Entity 등을 위해 필수
    kotlin("kapt")
    id("org.springframework.boot") // 라이브러리 구체적인 버전 결정
    id("io.spring.dependency-management") // 의존성 관리
}

dependencies {
    api(project(":core"))
    implementation(project(":jpa"))
    implementation("org.springframework.boot:spring-boot-starter")

    // query dsl
    val queryDslVersion = "5.0.0"
    implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
    kapt("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
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
