package email.manager

import email.manager.model.UserSession
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.OAuthServerSettings
import io.ktor.server.auth.oauth
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respondText
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import io.github.cdimascio.dotenv.dotenv


fun Application.installConfiguration(httpClient: HttpClient, redirects: MutableMap<String, String>) {
    val dotenv = dotenv {
        directory = "src/main/resources/.env"
    }
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
            cause.printStackTrace()
        }
    }
    install(Sessions) {
        cookie<UserSession>("user_session")
    }
    install(Authentication) {
        oauth("auth-oauth-google") {
            urlProvider = { "http://localhost:8080/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    clientId = dotenv.get("CLIENT_ID"),
                    clientSecret = dotenv.get("CLIENT_SECRET"),
                    defaultScopes = listOf(
                        "https://www.googleapis.com/auth/userinfo.profile",
                        "https://www.googleapis.com/auth/gmail.readonly"
                    ),
                    extraAuthParameters = listOf(
                        "access_type" to "offline"
                    ),
                    onStateCreated = { call, state ->
                        val redirectUrl = call.request.queryParameters["redirectUrl"]
                        redirects[state] = redirectUrl ?: "/"
                    }
                )
            }
            client = httpClient
        }
    }
}