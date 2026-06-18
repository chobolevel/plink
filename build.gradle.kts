import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val kotlinVersion: String by project
val springBootVersion: String by project
val springDependencyManagementVersion: String by project
val ktlintVersion: String by project

plugins {
    val kotlinVersion = "1.9.22"
    val springBootVersion = "3.5.5"
    val springDependencyManagementVersion = "1.1.7"
    val ktlintVersion = "11.3.1"
    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("kapt") version kotlinVersion apply false
    id("org.springframework.boot") version springBootVersion apply false
    id("io.spring.dependency-management") version springDependencyManagementVersion apply false
    id("org.jlleitschuh.gradle.ktlint") version ktlintVersion apply false
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
    // kotlin("jvm")과 kotlin("plugin.spring")은 각 모듈 plugins {} 블록에서 선언
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
