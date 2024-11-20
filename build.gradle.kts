import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinx.serialization)
}

group = "com.example"
version = "0.0.1"

// Load properties from local.properties if it exists
val localPropertiesFile = rootProject.file("local.properties")
val localProperties = Properties()

if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

// Resolve database properties, prioritizing environment variables
val dbUrl: String = System.getenv("DB_URL") ?: localProperties.getProperty("DB_URL", "jdbc:postgresql://localhost:5432/tasksdatabase")
val dbUser: String = System.getenv("DB_USER") ?: localProperties.getProperty("DB_USER", "localUser")
val dbPassword: String = System.getenv("DB_PASSWORD") ?: localProperties.getProperty("DB_PASSWORD", "localPassword")
val dbDriver: String = System.getenv("DB_DRIVER") ?: localProperties.getProperty("DB_DRIVER", "org.postgresql.Driver")

application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf(
        "-Dio.ktor.development=$isDevelopment",
        "-Ddb.url=$dbUrl",
        "-Ddb.user=$dbUser",
        "-Ddb.password=$dbPassword",
        "-Ddb.driver=$dbDriver"
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
