package com.example.plugins

import com.example.model.Priority
import com.example.model.Task
import com.example.model.TaskRepository
import com.example.model.tasksAsTable
import com.sun.tools.javac.util.Log
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.log
import io.ktor.server.http.content.staticResources
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {

        staticResources("/task-ui", "task-ui")

        route("/tasks") {

            get {
                val tasks = TaskRepository.allTasks()
                call.respondText(
                    contentType = ContentType.parse("text/html"),
                    text = tasks.tasksAsTable()
                )
            }

            get("/byName/{taskName}") {
                val name = call.parameters["taskName"]
                if (name == null) {
                    call.respondText("Task name not specified", status = HttpStatusCode.BadRequest)
                    return@get
                }

                val task = TaskRepository.taskByName(name)
                if (task == null) {
                    call.respondText("Task not found", status =HttpStatusCode.NotFound)
                    return@get
                }

                call.respondText(
                    contentType = ContentType.parse("text/html"),
                    text = listOf(task).tasksAsTable()
                )
            }

            get("/byPriority/{priority}") {
                val priorityAsText = call.parameters["priority"]
                if (priorityAsText == null) {
                    call.respondText("Priority not specified", status = HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val priority = Priority.valueOf(priorityAsText)
                    val tasks = TaskRepository.tasksByPriority(priority)

                    if (tasks.isEmpty()) {
                        call.respondText(
                            "No tasks found for this priority",
                            status = HttpStatusCode.NotFound
                        )
                        return@get
                    }

                    call.respondText(
                        contentType = ContentType.parse("text/html"),
                        text = tasks.tasksAsTable()
                    )
                } catch (ex: IllegalArgumentException) {
                    call.respondText("Invalid priority value", status = HttpStatusCode.BadRequest)
                }
            }

            post {
                val formContent = call.receiveParameters()
                log.debug("Received form content: $formContent")

                val params = Triple(
                    formContent["name"] ?: "",
                    formContent["description"] ?: "",
                    formContent["priority"] ?: ""
                )

                if (params.toList().any { it.isEmpty() }) {
                    log.warn("Missing parameters in form submission")
                    call.respondText("Missing parameters", status = HttpStatusCode.BadRequest)
                    return@post
                }

                try {
                    val priority = Priority.valueOf(params.third)

                    log.info("Adding task: $params")
                    log.info("Priority: $priority")

                    TaskRepository.addTask(
                        Task(
                            name = params.first,
                            description = params.second,
                            priority = priority
                        )
                    )

                    call.respondText("Task added", status = HttpStatusCode.NoContent)
                } catch (ex: IllegalArgumentException) {
                    log.error("Invalid priority value: ${params.third}", ex)
                    call.respondText("Invalid priority value", status = HttpStatusCode.BadRequest)
                } catch (ex: IllegalStateException) {
                    log.warn("Task with name ${params.first} already exists", ex)
                    call.respondText(
                        "Task with this name already exists",
                        status = HttpStatusCode.BadRequest
                    )
                }
            }
        }
    }
}
