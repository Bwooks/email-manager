package email.manager

import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import email.manager.kafka.consumer.EmailConsumerService
import email.manager.routes.appAuthRoutes
import email.manager.routes.googleAuthRoutes
import io.ktor.server.http.content.staticFiles
import io.ktor.server.response.respondFile
import java.io.File
import kotlinx.coroutines.runBlocking


class App {
    private var applicationHttpClient = createHttpClientWithHeaders()
    private val redirectsMap = mutableMapOf<String, String>()

    fun startServer() {
        val currentDirectory = File("").absolutePath
        val rootDirectory = File(currentDirectory).parentFile.parentFile

        embeddedServer(Netty, port = 8080) {
            installConfiguration(applicationHttpClient, redirectsMap)
            routing {
                googleAuthRoutes(redirectsMap)
                appAuthRoutes()

                get("/") {
                    val targetFile = File(rootDirectory, "client/build/index.html")
                    call.respondFile(targetFile)
                }
                staticFiles("/static", File(rootDirectory, "client/build/static"))

            }
        }.start(wait = true)

    }
}

fun main() = runBlocking {
    val app = App()
    val consumerService = EmailConsumerService(listOf("email-messages-1"), "email-message-consumers")
    DatabaseFactory.init()
    consumerService.init()
    app.startServer()
}
