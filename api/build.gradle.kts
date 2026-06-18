plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":core"))
    // 프로그램 실행 중에 자기 자신의 구조(클래스 이름, 프로퍼티 목록, 어노테이션 정보 등)을 들여다보고 조작할 수 있게 해주는 라이브러리
    // JSON 직렬화/역직렬화, JPA/Hibernate, 어노테이션 기반 로직 등에 필요
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.16")
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    runtimeOnly("com.mysql:mysql-connector-j")
}
