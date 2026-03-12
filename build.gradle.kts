import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    val kotlinVersion = "1.9.22"
    val springBootVersion = "3.5.5"
    val springDependencyManagementVersion = "1.1.7"
    val ktlintVersion = "11.3.1"
    id("org.springframework.boot") version springBootVersion apply false
    id("io.spring.dependency-management") version springDependencyManagementVersion apply false
    id("org.jlleitschuh.gradle.ktlint") version ktlintVersion apply false
    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion apply false
    // apply false = 루트에서 플러그인 선언만 하고 실제 적용은 서브 모듈의 build.gradle.kts에서 결정
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    group = "com.plink"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions {
            freeCompilerArgs.set(listOf("-Xjsr305=strict"))
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
