package com.example.rutour_diplom

import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailSender(private val username: String, private val password: String) {

    fun sendEmail(to: String, subject: String, body: String) {
        val props = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", "smtp.yandex.ru")
            put("mail.smtp.port", "587")
            put("mail.smtp.ssl.trust", "smtp.yandex.ru")
        }

        val session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password)
            }
        })

        try {
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(username))
                setRecipients(Message.RecipientType.TO, InternetAddress.parse(to))
                setSubject(subject)
                setText(body)
            }

            Transport.send(message)
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Ошибка при отправке сообщения: ${e.message}")
        }
    }
}
