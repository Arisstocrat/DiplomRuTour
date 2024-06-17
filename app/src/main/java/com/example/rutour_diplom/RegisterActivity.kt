package com.example.rutour_diplom

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rutour_diplom.databinding.ActivityLoginBinding
import com.example.rutour_diplom.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonOk.setOnClickListener {
            if (binding.textViewEmail.text.toString().isEmpty() || binding.textViewPass.text.toString().isEmpty()) {
                Toast.makeText(applicationContext, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.textViewEmail.text.toString(), binding.textViewPass.text.toString()
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userInfo = hashMapOf(
                            "email" to binding.textViewEmail.text.toString(),
                            "username" to binding.textViewLog.text.toString()
                        )

                        FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().currentUser?.uid.orEmpty())
                            .setValue(userInfo)
                            .addOnCompleteListener { dbTask ->
                                if (dbTask.isSuccessful) {
                                    Toast.makeText(applicationContext, "User registered and data saved", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                                } else {
                                    Toast.makeText(applicationContext, "Failed to save user data", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(applicationContext, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }



    fun onClick2(view: View) {  val intent= Intent (this, LoginActivity::class.java)
        startActivity(intent)}
}
