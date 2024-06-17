package com.example.rutour_diplom

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rutour_diplom.databinding.ActivityRegisterBinding
import com.example.rutour_diplom.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            if (binding.emailEt.text.toString().isEmpty() || binding.passwordEt.text.toString().isEmpty()) {
                Toast.makeText(applicationContext, "Поля не должны быть пустыми", Toast.LENGTH_SHORT).show()
            } else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.emailEt.text.toString(), binding.passwordEt.text.toString()
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        Toast.makeText(applicationContext, "Добро Пожаловать!", Toast.LENGTH_LONG).show()
                    }
                    else {
                        // Обработка ошибок, таких как неверный пароль или аккаунт не найден
                        Toast.makeText(applicationContext, "Ошибка входа, неверно введены данные", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    fun onClick1(view: View) {
        val intent= Intent (this, RegisterActivity::class.java)
        startActivity(intent)
    }
}