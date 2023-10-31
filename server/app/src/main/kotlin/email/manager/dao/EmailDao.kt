package email.manager.dao

import email.manager.model.Email
import email.manager.tables.EmailsTable
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class EmailDao {
    fun getEmailById(id: Int): Query {
        return transaction {
            EmailsTable.select { EmailsTable.id eq id }
        }
    }

    fun getAllEmails(): Query = transaction {
        EmailsTable.selectAll()
    }

    fun insertEmail(email: Email) {
        try {
            transaction {
                EmailsTable.insert {
                    it[messagePreview] = email.messagePreview
                    it[from] = email.from
                    it[to] = email.to
                    it[subject] = email.subject
                    it[externalMessageId] = email.externalMessageId
                    it[userId] = email.userId
                }
            }
        } catch(e: Exception) {
            println("TRANSACTION FAILED: $e")
        }
    }
}