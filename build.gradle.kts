plugins {
    val kotlinVersion = "2.2.21"
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.jlleitschuh.gradle.ktlint") version "14.0.1"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
}

group = "com"
version = "0.0.1-SNAPSHOT"
description = "plink"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // jwt
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("javax.xml.bind:jaxb-api:2.3.1")

    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // spring doc(register of doc)
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")

    // actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // query dsl
    val queryDslVersion = "5.0.0"
    implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
    kapt("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")

    // envers
    implementation("org.springframework.data:spring-data-envers")

    // jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // AWS
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")

    // jasypt
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

    // jsoup
    implementation("org.jsoup:jsoup:1.17.2")

    // db-mysql
    runtimeOnly("com.mysql:mysql-connector-j")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
