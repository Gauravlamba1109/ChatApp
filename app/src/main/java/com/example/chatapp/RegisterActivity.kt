package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var firebaseAuthID: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val toolbar: Toolbar = findViewById(R.id.toolbar_register)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Register"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setTitleTextColor(getResources().getColor(R.color.white))
        toolbar.navigationIcon?.setTint(getResources().getColor(R.color.white))
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, Welcome::class.java)
            startActivity(intent)
            finish()
        }


        mAuth = FirebaseAuth.getInstance()

    }

    fun signin(view: android.view.View) {
        val username_ = findViewById<EditText>(R.id.username_regis)
        val username: String = username_.text.toString()

        val emailr = findViewById<EditText>(R.id.email_regis)
        val email: String = emailr.text.toString()

        val pass = findViewById<EditText>(R.id.pass_regis)
        val password: String = pass.text.toString()

        if (username == "") {
            Toast.makeText(this, "Please enter valid username", Toast.LENGTH_LONG).show()
        } else if (email == "") {
            Toast.makeText(this, "Please enter valid email", Toast.LENGTH_LONG).show()
        } else if (password == "") {
            Toast.makeText(this, "Please enter valid password", Toast.LENGTH_LONG).show()
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        firebaseAuthID = mAuth.currentUser!!.uid
                        val databaseUrl = "https://chatapp-40111-default-rtdb.asia-southeast1.firebasedatabase.app"
                        refUsers = FirebaseDatabase.getInstance(databaseUrl).reference.child("Users")
                            .child(firebaseAuthID)

                        val userHashMap = HashMap<String, Any>()
                        userHashMap["uid"] = firebaseAuthID
                        userHashMap["username"] = username
                        userHashMap["profile"] =
                            "https://firebasestorage.googleapis.com/v0/b/chatapp-40111.appspot.com/o/user.png?alt=media&token=2f778886-619f-4956-8ddc-afb9a024bb92"
                        userHashMap["cover"] =
                            "https://firebasestorage.googleapis.com/v0/b/chatapp-40111.appspot.com/o/cover.png?alt=media&token=7096a28d-c917-49df-a385-df83cf2e3cfb"
                        userHashMap["status"] = "offline"
                        userHashMap["search"] = username.toLowerCase()
                        userHashMap["instagram"] = "https://m.instagram.com"
                        userHashMap["facebook"] = "https://m.facebook.com"
                        userHashMap["website"] = "https://m.google.com"

                        refUsers.updateChildren(userHashMap)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                    } else {
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