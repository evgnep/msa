import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10" apply false
}

group = "ru.otus.grpc"
version = "1.0-SNAPSHOT"

ext["grpcVersion"] = "1.47.0"
ext["grpcKotlinVersion"] = "1.3.0" // CURRENT_GRPC_KOTLIN_VERSION
ext["protobufVersion"] = "3.21.2"
ext["coroutinesVersion"] = "1.6.4"


allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}
