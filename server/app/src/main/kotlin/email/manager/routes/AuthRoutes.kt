package email.manager.routes

import email.manager.model.UserSession
import io.ktor.server.application.call
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set

fun Route.googleAuthRoutes(redirects: MutableMap<String, String>) {
    authenticate("auth-oauth-google") {
        get("/login") {

        }
        get("/callback") {
            val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
            val session = UserSession(principal!!.state!!, principal.accessToken)
            call.sessions.set(session)
            val redirect = redirects[principal.state!!]
            call.respondRedirect(redirect!!)
        }
    }
}

fun Route.appAuthRoutes() {
    post("/signup") {

    }
    post("/signin") {

    }
    post("/forgot-password") {

    }
}
