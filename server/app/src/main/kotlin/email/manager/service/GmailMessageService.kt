package email.manager.service

import email.manager.createHttpClientWithHeaders
import email.manager.model.GmailMessageResponse
import email.manager.dao.GmailMessageDao
import email.manager.model.GmailMessageIDListResponse
import email.manager.model.GoogleUserInfo
import email.manager.model.UserSession
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.http.HttpHeaders

class GmailMessageService() {
    private val gmailMessageDao = GmailMessageDao()
    private val GMAIL_BASE_API = "https://gmail.googleapis.com/gmail/v1/users"
    private val httpClient = createHttpClientWithHeaders()

    suspend fun fetchUser(userSession: UserSession): GoogleUserInfo {
        val userInfo = httpClient.request("https://www.googleapis.com/oauth2/v2/userinfo") {
            headers {
                append(HttpHeaders.Authorization, "Bearer ${userSession.token}")
            }
        }.body<GoogleUserInfo>()

        return userInfo
    }

    suspend fun fetchGmailMessageById(messageId: String, userSession: UserSession): GmailMessageResponse {
        val userInfo = fetchUser(userSession)
        val gmailMessage = httpClient.request("${GMAIL_BASE_API}/${userInfo.id}/messages/$messageId") {
            headers {
                append(HttpHeaders.Authorization, "Bearer ${userSession.token}")
            }
        }.body<GmailMessageResponse>()

        return gmailMessage
    }

    suspend fun fetchGmailMessagesForUser(userSession: UserSession): List<GmailMessageResponse> {
        val userInfo = fetchUser(userSession)
        val messageList = httpClient.request("${GMAIL_BASE_API}/${userInfo.id}/messages") {
            headers {
                append(HttpHeaders.Authorization, "Bearer ${userSession.token}")
            }
        }.body<GmailMessageIDListResponse>()

        val gmailMessages: List<GmailMessageResponse> = messageList.messages.map {
            fetchGmailMessageById(it.id, userSession)
        }

        return gmailMessages
    }
    fun insertGmailMessage(message: GmailMessageResponse) {
        gmailMessageDao.insertGmailMessage(message)
    }
}