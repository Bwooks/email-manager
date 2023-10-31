package email.manager.repository

import email.manager.dao.EmailDao
import email.manager.tables.EmailsTable
import email.manager.model.Email
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.transactions.transaction

class EmailRepository {
    private val emailDao = EmailDao()
    fun getEmailById(id: Int): Email? {
        val email = emailDao.getEmailById(id)
        return email.map{
            toEmail(it)
        }.singleOrNull()
    }

    fun getAllEmails(): List<Email> = transaction {
        val allEmails = emailDao.getAllEmails()
        allEmails.map { toEmail(it) }
    }

    fun insertEmail(email: Email) {
        emailDao.insertEmail(email)
    }

    private fun toEmail(row: ResultRow): Email {
        return Email(
            id = row[EmailsTable.id],
            messagePreview = row[EmailsTable.messagePreview],
            subject = row[EmailsTable.subject],
            from = row[EmailsTable.from],
            to = row[EmailsTable.to],
            userId = row[EmailsTable.userId],
            externalMessageId = row[EmailsTable.externalMessageId]
        )
    }
}