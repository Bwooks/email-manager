package email.manager.model

import java.util.Date

data class User(
    val id: Int? = null,
    val emailAddress: String,
    val dateCreated: Date,
    val oAuthAccessToken: String,
    val oAuthRefreshToken: String,
)

data class UserSession(
    val state: String,
    val token: String
)
