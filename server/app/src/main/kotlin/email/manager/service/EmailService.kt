package email.manager.service

import email.manager.model.Email
import email.manager.repository.EmailRepository
import email.manager.model.GmailMessageResponse
import email.manager.model.HeaderField

class EmailService {
    private val emailDao = EmailRepository()

    fun insertEmail(email: Email) {
        emailDao.insertEmail(email)
    }

    fun emailFromGmailMessage(gmailMessage: GmailMessageResponse): Email {
        val fromHeader = gmailMessage.getHeader(HeaderField.FROM)
        val toHeader = gmailMessage.getHeader(HeaderField.TO)
        val subjectHeader = gmailMessage.getHeader(HeaderField.SUBJECT)

        return Email(
            messagePreview = gmailMessage.snippet,
            from = fromHeader?.value ?: "",
            to = toHeader?.value ?: "",
            subject = subjectHeader?.value ?: "",
            externalMessageId = gmailMessage.id,
            userId = 1,
        )
    }
}