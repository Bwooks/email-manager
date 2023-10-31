package email.manager.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object GmailMessagesTable: Table() {
    val id = varchar("id", 255).primaryKey()
    val labelIds = varchar("label_ids", 255)
    val snippet = text("snippet")
    val payload = text("payload")
    val sizeEstimate = integer("size_estimate")
    val historyId = varchar("history_id", 128)
    val internalDate = date("internal_date")
}