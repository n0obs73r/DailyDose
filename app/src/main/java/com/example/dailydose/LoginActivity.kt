package com.example.dailydose

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button : Button = findViewById(R.id.Login)
        button.setOnClickListener {
            login()
        }
    }

    private fun login(){
        var user = findViewById<EditText>(R.id.login_email).text.toString()
        var password = findViewById<EditText>(R.id.login_password).text.toString()

        auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(user, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Logged In", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Username/Password Incorrect" + task.exception!!.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}