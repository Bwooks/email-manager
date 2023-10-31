package email.manager

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import email.manager.tables.EmailsTable
import email.manager.tables.GmailMessagesTable
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/email_manager_db",
            driver = "org.postgresql.Driver",
            user = "brooksdulla",
        )

//        transaction {
//            SchemaUtils.create(
//                EmailsTable,
//                GmailMessagesTable
//            )
//        }
    }
}
