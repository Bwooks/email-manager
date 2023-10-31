package email.manager.dao

import com.google.gson.Gson
import email.manager.model.GmailMessageResponse
import email.manager.tables.GmailMessagesTable
import java.time.Instant
import java.time.ZoneId
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class GmailMessageDao {
    private val gson = Gson()
    fun insertGmailMessage(message: GmailMessageResponse) = transaction {
        try {
            println("INSERTING GMAIL MESSAGE")
            GmailMessagesTable.insert {
                it[id] = message.id
                it[snippet] = message.snippet
                it[sizeEstimate] = message.sizeEstimate
                it[historyId] = message.historyId
                it[internalDate] = Instant.ofEpochMilli(message.internalDate.toLong()).atZone(ZoneId.systemDefault()).toLocalDate()
                it[labelIds] = message.labelIds.joinToString(",")
                it[payload] = gson.toJson(message.payload)
            }
        } catch(e: Exception) {
            println("CATCHING EXCP: $e")
        }
    }
}