package com.example.mychittor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var editText: EditText
    private fun login(){


        var userText : EditText = findViewById(R.id.editTextTextEmailAddress)
        var passText : EditText = findViewById(R.id.Password)
        val email : Editable? = userText.getText()
        val password : Editable? = passText.getText()

        auth = FirebaseAuth.getInstance()
       // if (auth.getCurrentUser() != null) {
            // User is signed in (getCurrentUser() will be null if not signed in)
         //   val intent = Intent(this, MainActivity::class.java);
           // startActivity(intent);
            //finish();
       // }
        auth.signInWithEmailAndPassword(email.toString(), password.toString()).addOnCompleteListener(this, OnCompleteListener{ task ->
            if(task.isSuccessful){
                Toast.makeText(this, "Logged In", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else {
                Toast.makeText(this, "Username/Password Incorrect"+ task.exception!!.message, Toast.LENGTH_LONG).show()
            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val button : Button = findViewById(R.id.Login)

        button.setOnClickListener { login() }
    }
}