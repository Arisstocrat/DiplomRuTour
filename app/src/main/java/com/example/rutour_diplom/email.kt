package com.example.rutour_diplom

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class email : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_email)

        // Инициализация EmailSender с вашими учетными данными
        val emailSender = EmailSender("diplomRuTour@yandex.ru", "okdjnbdibvsrivja")

        val toEditText: EditText = findViewById(R.id.toEditText)
        val subjectEditText: EditText = findViewById(R.id.subjectEditText)
        val bodyEditText: EditText = findViewById(R.id.bodyEditText)
        val sendButton: Button = findViewById(R.id.sendButton)

        sendButton.setOnClickListener {
            val to = toEditText.text.toString()
            val subject = subjectEditText.text.toString()
            val body = bodyEditText.text.toString()

            // Отправка email
            Thread {
                try {
                    emailSender.sendEmail(to, subject, body)
                    runOnUiThread {
                        Toast.makeText(this, "Ваше сообщение было отправлено", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    runOnUiThread {
                        Toast.makeText(this, "Ваше сообщение было отправлено", Toast.LENGTH_LONG).show()
                    }
                }
            }.start()
        }
    }

    fun onClickhot(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun onClick2(view: View) {
        val intent = Intent(this, ExcursionActivity::class.java)
        startActivity(intent)
    }
}
