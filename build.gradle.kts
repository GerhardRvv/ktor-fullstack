import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinx.serialization)
}

group = "com.example"
version = "0.0.1"

val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    val localProperties = Properties()
    localProperties.load(localPropertiesFile.inputStream())

    extra["dbUrl"] = localProperties["DB_URL"]
    extra["dbUser"] = localProperties["DB_USER"]
    extra["dbPassword"] = localProperties["DB_PASSWORD"]
    extra["dbDriver"] = localProperties["DB_DRIVER"]
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf(
        "-Dio.ktor.development=$isDevelopment",
        "-Ddb.url=${extra["dbUrl"]}",
        "-Ddb.user=${extra["dbUser"]}",
        "-Ddb.password=${extra["dbPassword"]}",
        "-Ddb.driver=${extra["dbDriver"]}"
    )
}

ktor {
    fatJar {
        archiveFileName.set("tasks-app.jar")
    }
}

tasks {
    create("stage").dependsOn("installDist")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.status.pages)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.ktor.server.websockets)

    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.ktor.server.test.host.jvm)
    testImplementation(libs.ktor.client.content.negotiation)
    testImplementation(libs.json.path)

    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.xml)
    implementation(libs.ktor.server.thymeleaf)

    implementation(libs.postgresql)
    implementation(libs.h2database)
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)

}
