package email.manager.model

import kotlinx.serialization.Serializable

@Serializable
data class GoogleUserInfo(
    val id: String,
    val name: String,
    val given_name: String,
    val family_name: String,
    val picture: String,
    val locale: String
)
