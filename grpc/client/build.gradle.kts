plugins {
    kotlin("jvm")
}

group = "ru.otus.grpc"

dependencies {
    implementation(project(":proto"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.ext["coroutinesVersion"]}")
    runtimeOnly("io.grpc:grpc-netty:${rootProject.ext["grpcVersion"]}")

}

tasks.withType<Test> {
    useJUnitPlatform()
}