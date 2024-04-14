package com.example.triversitylingo2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import android.content.Intent
import com.example.triversitylingo2.Models.Model

class RegisterActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var password: EditText
    private lateinit var email: EditText
    private lateinit var submit: Button
    private lateinit var reset: Button
    private lateinit var login: Button
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //declare all component
        name = findViewById(R.id.etName)
        password = findViewById(R.id.etPassword)
        email = findViewById(R.id.etEmail)
        submit = findViewById(R.id.btnSubmit)
        reset = findViewById(R.id.btnReset)
        login = findViewById(R.id.btnLogin)
        //popup.message when click button submit
        Toast.makeText((this), "Submit", Toast.LENGTH_LONG).show()
        submit.setOnClickListener {
            saveData(email.text.toString(), name.text.toString(),
                password.text.toString())
        }
        reset.setOnClickListener {
            name.setText("")
            password.setText("")
            email.setText("")
        }

        login.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    //create the function saveData
    // this function sne data to firebase
    //n = name
    //p = password
    //e = email
    //t = phone
    private fun saveData(e:String, n:String, p:String)
    {
        //getInstance = get object
        //customer refer to table
        //customer can change to other name
        //link database named customer
        dbRef = FirebaseDatabase.getInstance().getReference("Customer")
        val customerId = dbRef.push().key!!
        //produce auto generate customer if
        //!! refer must had recovery or id cannot null
        val em = Model(e, customerId, n, p)
        dbRef.child(customerId).setValue(em)
            .addOnCompleteListener {
                Toast.makeText(this,"Success", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this,"Failure", Toast.LENGTH_LONG).show()
            }
        val i = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(i)
    }
}