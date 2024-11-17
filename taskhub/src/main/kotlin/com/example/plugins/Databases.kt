package com.example.plugins

import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {
    val dbDriver = environment.config.property("ktor.database.driver").getString()
    val dbUrl = System.getProperty("db.url")
    val dbUser = System.getProperty("db.user")
    val dbPassword = System.getProperty("db.password")

    Database.connect(
        url = dbUrl,
        driver = dbDriver,
        user = dbUser,
        password = dbPassword
    )
}