package email.manager.model
data class Email(
    val id: Int? = null,
    val messagePreview: String,
    val subject: String,
    val from: String,
    val to: String,
    val userId: Int,
    val externalMessageId: String
)

enum class HeaderField(val fieldName: String) {
    SUBJECT("Subject"),
    FROM("From"),
    TO("To"),
    MESSAGE_ID("Message-Id")
}