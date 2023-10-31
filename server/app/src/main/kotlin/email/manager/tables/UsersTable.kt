package email.manager.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object UsersTable: Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val emailAddress = varchar("email_address", 255)
    val deleted = bool("deleted")
    val dateCreated = date("date_created")
    val externalUserId = varchar("external_user_id", 255)
    val oauthAccessToken = varchar("oauth_access_token", 255)
    val oauthRefreshToken = varchar("oath_refresh_token", 255)
}