package email.manager.model

import kotlinx.serialization.Serializable

@Serializable
data class GmailMessageIDListResponse(
    val messages: List<GmailMessageIDListItem>,
    val nextPageToken: String,
    val resultSizeEstimate: Int
)
@Serializable
data class GmailMessageIDListItem (
    val id: String,
    val threadId: String
)

@Serializable
data class GmailMessageResponse(
    val id: String,
    val threadId: String,
    val labelIds: List<String>,
    val snippet: String,
    val payload: MessagePayload,
    val sizeEstimate: Int,
    val historyId: String,
    val internalDate: String
) {
    fun getHeader(field: HeaderField): Header? {
        return payload.headers.find {
            it.name.equals(field.fieldName, ignoreCase = true)
        }
    }
}
@Serializable
data class MessagePayload(
    val partId: String,
    val mimeType: String,
    val fileName: String? = "",
    val headers: List<Header>,
    val body: Body,
    val parts: List<Part>? = listOf()
)
@Serializable
data class Part(
    val partId: String,
    val mimeType: String,
    val fileName: String? = "",
    val headers: List<Header>,
    val body: Body
)
@Serializable
data class Body(
    val size: Int,
    val data: String? = null
)
@Serializable
data class Header (
    val name: String,
    val value: String
)