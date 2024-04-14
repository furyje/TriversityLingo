package com.example.triversitylingo2.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.triversitylingo2.MainActivity
import com.example.triversitylingo2.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val totalScore = intent.getIntExtra("total", 0)
        val correctAnsw = intent.getIntExtra("score", 0)
        val wrong = totalScore - correctAnsw

        binding.totalQuestions.text = totalScore.toString()
        binding.rightAnsw.text = correctAnsw.toString()
        binding.wrongAnsw.text = wrong.toString()

        binding.btnRetry.setOnClickListener {
            val intent = Intent(this@ScoreActivity, SetsActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnQuit.setOnClickListener {
            val intent = Intent(this@ScoreActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
