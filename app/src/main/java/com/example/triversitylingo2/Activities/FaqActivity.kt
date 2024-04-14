package com.example.triversitylingo2.Activities

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.triversitylingo2.R

class FaqActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)

        val videoView = findViewById<VideoView>(R.id.videoView)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.fypvid)
        videoView.setVideoURI(videoUri)

        val openYouTubeButton = findViewById<Button>(R.id.openYouTubeButton)
        openYouTubeButton.setOnClickListener {
            videoView.visibility = View.VISIBLE
            videoView.start()

            // Hide the text questions and answers
            hideTextQuestionsAndAnswers()
        }

        val goBackButton = findViewById<Button>(R.id.goBackButton)
        goBackButton.setOnClickListener {
            finish()
        }
    }

    private fun hideTextQuestionsAndAnswers() {
        val textView2 = findViewById<View>(R.id.textView2)
        val textView3 = findViewById<View>(R.id.textView3)
        val textView4 = findViewById<View>(R.id.textView4)
        val textView5 = findViewById<View>(R.id.textView5)
        val textView6 = findViewById<View>(R.id.textView6)

        textView2.visibility = View.GONE
        textView3.visibility = View.GONE
        textView4.visibility = View.GONE
        textView5.visibility = View.GONE
        textView6.visibility = View.GONE
    }
}
