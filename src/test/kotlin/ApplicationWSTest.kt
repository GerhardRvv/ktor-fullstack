//import com.example.model.Priority
//import com.example.model.Task
//import com.example.module
//import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
//import io.ktor.client.plugins.websocket.WebSockets
//import io.ktor.client.plugins.websocket.webSocket
//import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
//import io.ktor.client.plugins.websocket.converter
//import io.ktor.serialization.deserialize
//import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
//import io.ktor.serialization.kotlinx.json.json
//import io.ktor.server.testing.testApplication
//import kotlinx.coroutines.flow.consumeAsFlow
//import kotlinx.coroutines.flow.map
//import kotlinx.coroutines.flow.scan
//import kotlinx.serialization.json.Json
//
//import kotlin.test.*
//
//class ApplicationWSTest {
//    @Test
//    fun testRoot() = testApplication {
//        application {
//            module()
//        }
//
//        val client = createClient {
//            install(ContentNegotiation) {
//                json()
//            }
//            install(WebSockets) {
//                contentConverter =
//                    KotlinxWebsocketSerializationConverter(Json)
//            }
//        }
//
//        val expectedTasks = listOf(
//            Task(0,"cleaning", "Clean the house", Priority.Low),
//            Task(0,"gardening", "Mow the lawn", Priority.Medium),
//            Task(0,"shopping", "Buy the groceries", Priority.High),
//            Task(0,"painting", "Paint the fence", Priority.Medium)
//        )
//        var actualTasks = emptyList<Task>()
//
//        client.webSocket("/tasks") {
//            consumeTasksAsFlow().collect { allTasks ->
//                actualTasks = allTasks
//            }
//        }
//
//        assertEquals(expectedTasks.size, actualTasks.size)
//        expectedTasks.forEachIndexed { index, task ->
//            assertEquals(task, actualTasks[index])
//        }
//    }
//
//    private fun DefaultClientWebSocketSession.consumeTasksAsFlow() = incoming
//        .consumeAsFlow()
//        .map {
//            converter!!.deserialize<Task>(it)
//        }
//        .scan(emptyList<Task>()) { list, task ->
//            list + task
//        }
//}
