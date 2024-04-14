package com.example.triversitylingo2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.triversitylingo2.Models.Model
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    private lateinit var signup: Button // Added button for signup
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //declare components
        name = findViewById(R.id.emailLogin)
        password = findViewById(R.id.PasswordLogin)
        login = findViewById(R.id.btnLogin)
        signup = findViewById(R.id.btnRegister) // Initialize the signup button

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("Customer")

        // Login button click listener
        login.setOnClickListener {
            val name = name.text.toString()
            val password = password.text.toString()

            if (name.isNotEmpty() && password.isNotEmpty()) {
                logIn(name, password)
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "All fields are mandatory", Toast.LENGTH_LONG
                ).show()
            }
        }

        // Signup button click listener
        signup.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun logIn(name: String, password: String) {
        databaseReference.orderByChild("customerName").equalTo(name)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (custSnapshot in snapshot.children) {
                            val model = custSnapshot.getValue(Model::class.java)
                            if (model != null && model.customerPassword == password) {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Login Successful",
                                    Toast.LENGTH_LONG
                                ).show()
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.putExtra("USERNAME", name) // Pass the user's name as an extra
                                startActivity(intent)
                                finish()
                                return
                            }
                        }
                    }
                    Toast.makeText(
                        this@LoginActivity,
                        "Incorrect username or password",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Database Error ${databaseError.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

}
