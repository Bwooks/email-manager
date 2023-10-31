package email.manager

import email.manager.model.UserSession
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClientWithHeaders(commonHeaders: Map<String, String>? = null): HttpClient {
    val ignoringUnknownKeysJson = Json { ignoreUnknownKeys = true }

    return HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(ignoringUnknownKeysJson)
        }
        if(!commonHeaders.isNullOrEmpty()) {
            defaultRequest {
                headers {
                    commonHeaders.entries.forEach{
                        this.append(it.key, it.value)
                    }
                }
            }
        }
    }
}