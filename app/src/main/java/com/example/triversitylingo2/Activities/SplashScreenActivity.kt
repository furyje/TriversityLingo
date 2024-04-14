package com.example.triversitylingo2.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.triversitylingo2.LoginActivity
import com.example.triversitylingo2.MainActivity
import com.example.triversitylingo2.R


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide() // Use safe call operator instead of !!
        Handler().postDelayed({
            val intent = Intent(
                this@SplashScreenActivity,
                LoginActivity::class.java
            )
            startActivity(intent)
            finish() // optional, depending on whether you want to finish the splash activity
        }, 2000)
    }

}
