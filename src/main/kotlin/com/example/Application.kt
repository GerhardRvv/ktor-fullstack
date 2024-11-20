package com.example

import com.example.model.PostgresTaskRepository
import com.example.plugins.configureDatabases
import com.example.plugins.configureRouting
//import com.example.plugins.configureSerialization
//import com.example.plugins.configureSockets
//import com.example.plugins.configureTemplating
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
//    val repository = FakeTaskRepository() // Use to test without DB
    configureDatabases()

    val repository = PostgresTaskRepository()
//    configureSockets(repository)
//    configureTemplating(repository)

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
//    configureSerialization(repository)
    configureRouting(repository)
}
