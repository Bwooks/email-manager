package email.manager.tables
import org.jetbrains.exposed.sql.Table

object EmailsTable: Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val messagePreview = varchar("message_preview", 255)
    val subject = varchar("subject", 255)
    val from = varchar("from", 255)
    val to = varchar("to", 255)
    val userId = reference("user_id", UsersTable.id)
    val externalMessageId = reference("gmail_message_id", GmailMessagesTable.id)
}