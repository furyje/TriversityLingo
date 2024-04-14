package com.example.triversitylingo2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.triversitylingo2.Activities.FaqActivity
import com.example.triversitylingo2.Activities.SetsActivity
import com.example.triversitylingo2.Activities.SetsActivity2
import com.example.triversitylingo2.Activities.SetsActivity3
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {

    private lateinit var mandarin: MaterialCardView
    private lateinit var arabic: MaterialCardView
    private lateinit var spanish: MaterialCardView
    private lateinit var geography: MaterialCardView
    private lateinit var logoutButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        // Initialize views
        mandarin = findViewById(R.id.mandarin)
        arabic = findViewById(R.id.arabic)
        spanish = findViewById(R.id.spanish)
        geography = findViewById(R.id.geography)
        logoutButton = findViewById(R.id.logout_button) // Initialize the logout button

        // Retrieve user's name from Intent
        val userName = intent.getStringExtra("USERNAME")
        val textView = findViewById<TextView>(R.id.textView2)
        textView.text = "Choose Language To Learn, $userName"


        // Set click listeners
        mandarin.setOnClickListener {
            val intent = Intent(this@MainActivity, SetsActivity::class.java)
            startActivity(intent)
        }

        arabic.setOnClickListener {
            val intent = Intent(this@MainActivity, SetsActivity2::class.java)
            startActivity(intent)
        }

        spanish.setOnClickListener {
            val intent = Intent(this@MainActivity, SetsActivity3::class.java)
            startActivity(intent)
        }

        geography.setOnClickListener {
            val intent = Intent(this@MainActivity, FaqActivity::class.java)
            startActivity(intent)
        }
        logoutButton.setOnClickListener {
            // Redirect user to login page
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finish current activity to prevent going back to MainActivity on back press
        }
    }
}
