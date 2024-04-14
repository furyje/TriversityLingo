package com.example.triversitylingo2.Activities

import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.triversitylingo2.Models.QuestionModel
import com.example.triversitylingo2.R
import com.example.triversitylingo2.databinding.ActivityQuestionBinding

class QuestionActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionBinding
    private val list = ArrayList<QuestionModel>()
    private var count = 0
    private var position = 0
    private var score = 0
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        resetTimer()
        timer.start()

        val setName = intent.getStringExtra("setName")

        if (setName == "Greetings") {
            setOne()
        } else if (setName == "Food & Beverages") {
            setTwo()
        }

        if (list.isNotEmpty()) {
            playAnimation(binding.question, 0, list[position].question)
        } else {
            // Handle the case when the list is empty
        }

        binding.btnNext.setOnClickListener {
            // Your existing code for handling btnNext click
        }


        for (i in 0 until 4) {
            binding.optionContainer.getChildAt(i).setOnClickListener {
                checkAnswer(it as Button)
            }
        }

        playAnimation(binding.question, 0, list[position].question)

        binding.btnNext.setOnClickListener {
            if (::timer.isInitialized) {
                timer.cancel()
            }
            timer.start()

            binding.btnNext.isEnabled = false
            binding.btnNext.alpha = 0.3f
            enabledOption(true)
            position++

            if (position == list.size) {
                val intent = Intent(this@QuestionActivity2, ScoreActivity::class.java)
                intent.putExtra("score", score)
                intent.putExtra("total", list.size)
                startActivity(intent)
                finish()
                return@setOnClickListener
            }

            count = 0
            playAnimation(binding.question, 0, list[position].question)
        }
    }

    private fun resetTimer() {
        timer = object : CountDownTimer(38000, 1000) {
            override fun onTick(l: Long) {
                binding.timer.text = (l / 1000).toString()
            }

            override fun onFinish() {
                val dialog = Dialog(this@QuestionActivity2)
                dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.timeout_dialog)
                dialog.findViewById<View>(R.id.tryAgain).setOnClickListener {
                    val intent = Intent(this@QuestionActivity2, SetsActivity2::class.java)
                    startActivity(intent)
                    finish()
                }
                dialog.show()
            }
        }.start()
    }

    private fun playAnimation(view: View, value: Int, data: String) {
        view.animate().alpha(value.toFloat()).scaleX(value.toFloat()).scaleY(value.toFloat()).setDuration(500).setStartDelay(100)
            .setInterpolator(DecelerateInterpolator()).setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {
                    if (value == 0 && count < 4) {
                        val option: String = when (count) {
                            0 -> list[position].optionA
                            1 -> list[position].optionB
                            2 -> list[position].optionC
                            3 -> list[position].optionD
                            else -> ""
                        }
                        playAnimation(binding.optionContainer.getChildAt(count), 0, option)
                        count++
                    }
                }

                @SuppressLint("SetTextI18n")
                override fun onAnimationEnd(animator: Animator) {
                    if (value == 0) {
                        try {
                            (view as TextView).text = data
                            binding.totalQuestion.text = "${position + 1}/${list.size}"
                        } catch (e: Exception) {
                            (view as Button).text = data
                        }
                        view.tag = data
                        playAnimation(view, 1, data)
                    }
                }

                override fun onAnimationCancel(animator: Animator) {}
                override fun onAnimationRepeat(animator: Animator) {}
            })
    }

    private fun enabledOption(enable: Boolean) {
        for (i in 0 until 4) {
            binding.optionContainer.getChildAt(i).isEnabled = enable
            if (enable) {
                binding.optionContainer.getChildAt(i).setBackgroundResource(R.drawable.btn_opt)
            }
        }
    }

    private fun checkAnswer(selectedOption: Button) {
        if (::timer.isInitialized) {
            timer.cancel()
        }
        binding.btnNext.isEnabled = true
        binding.btnNext.alpha = 1f
        if (selectedOption.text.toString() == list[position].correctAnswer) {
            score++
            selectedOption.setBackgroundResource(R.drawable.right_answ)
        } else {
            selectedOption.setBackgroundResource(R.drawable.wrong_answ)
            val correctOption = binding.optionContainer.findViewWithTag<Button>(list[position].correctAnswer)
            correctOption.setBackgroundResource(R.drawable.right_answ)
        }
    }

    private fun setOne() {
        list.add(QuestionModel("What is the Arabic word for 'hello'?", "A. مرحبا (Marhaba) - mar-ha-ba", "B. صباح الخير (Sabah al-khair) - sa-bah al-khayr", "C. مساء الخير (Masa' al-khair) - ma-sa al-khayr", "D. شكرا (Shukran) - shook-rah", "A. مرحبا (Marhaba) - mar-ha-ba"))
        list.add(QuestionModel("Which phrase means 'goodbye' in Arabic?", "A. مرحبا (Marhaba) - mar-ha-ba", "B. وداعا (Wada'an) - wa-da-an", "C. شكرا (Shukran) - shook-rah", "D. مساء الخير (Masa' al-khair) - ma-sa al-khayr", "Answer: B. وداعا (Wada'an) - Goodbye"))
        list.add(QuestionModel("What does 'شكرا' (Shukran) mean in English?", "A. Thank you", "B. Hello", "C. Goodbye", "D. Sorry", "A. Thank you"))
        list.add(QuestionModel("How do you say 'yes' in Arabic?", "A. لا (La) - la", "B. نعم (Na'am) - na-am", "C. لم (Lam) - lam", "D. ما (Ma) - ma", "Answer: B. نعم (Na'am) - Yes"))
        list.add(QuestionModel("What does 'آسف' (Aasif) mean in English?", "A. Excuse me", "B. Yes", "C. Sorry", "D. Goodbye", "C. Sorry"))
    }

    private fun setTwo() {
        list.add(QuestionModel("Which character represents the number 'one' in Arabic?", "A. واحد (Waahid) - wa-heed", "B. اثنان (Ithnaan) - ith-naan", "C. ثلاثة (Thalaatha) - tha-la-tha", "D. أربعة (Arba'a) - ar-ba-a", "A. واحد (Waahid) - wa-heed"))
        list.add(QuestionModel("How do you say 'two' in Arabic?", "A. واحد (Waahid) - wa-heed", "B. اثنان (Ithnaan) - ith-naan", "C. ثلاثة (Thalaatha) - tha-la-tha", "D. أربعة (Arba'a) - ar-ba-a", "B. اثنان (Ithnaan) - ith-naan"))
        list.add(QuestionModel("What is the Arabic word for 'three'?", "A. واحد (Waahid) - wa-heed", "B. اثنان (Ithnaan) - ith-naan", "C. ثلاثة (Thalaatha) - tha-la-tha", "D. أربعة (Arba'a) - ar-ba-a", "C. ثلاثة (Thalaatha) - tha-la-tha"))
        list.add(QuestionModel("Which character means 'four' in Arabic?", "A. واحد (Waahid) - wa-heed", "B. اثنان (Ithnaan) - ith-naan", "C. ثلاثة (Thalaatha) - tha-la-tha", "D. أربعة (Arba'a) - ar-ba-a", "D. أربعة (Arba'a) - ar-ba-a"))
        list.add(QuestionModel("How do you write 'ten' in Arabic?", "A. عشرة (Ashara) - a-sha-ra", "B. مئة (Mi'ah) - meeah", "C. ألف (Alf) - alf", "D. مليون (Milyon) - mil-yon", "A. عشرة (Ashara) - a-sha-ra"))
    }




}