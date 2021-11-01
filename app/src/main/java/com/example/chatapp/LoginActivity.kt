package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private lateinit var refUsers : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val toolbar: Toolbar = findViewById(R.id.toolbar_login)
        setSupportActionBar(toolbar)
        supportActionBar!!.title="Login"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setTitleTextColor(getResources().getColor(R.color.white))
        toolbar.navigationIcon?.setTint(getResources().getColor(R.color.white))
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this,Welcome::class.java)
            startActivity(intent)
            finish()
        }

        mAuth = FirebaseAuth.getInstance()

    }

    fun login(view: android.view.View) {

        val username_ = findViewById<EditText>(R.id.user_login)
        val username: String = username_.text.toString()

        val pass = findViewById<EditText>(R.id.pass_login)
        val password: String = pass.text.toString()

        if (username == "") {
            Toast.makeText(this, "Please enter valid username", Toast.LENGTH_LONG).show()
        } else if (password == "") {
            Toast.makeText(this, "Please enter valid password", Toast.LENGTH_LONG).show()
        } else {
            mAuth.signInWithEmailAndPassword(username,password)
                .addOnCompleteListener{ task->
                    if(task.isSuccessful){
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(
                            this,
                            "Error: " + task.exception!!.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

    }
}