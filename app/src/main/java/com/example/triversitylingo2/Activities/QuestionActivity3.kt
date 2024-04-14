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

class QuestionActivity3 : AppCompatActivity() {

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
        } else if (setName == "Nationalities") {
            setThree()
        } else if (setName == "Occupations") {
            setFour()
        } else if (setName == "Like and Dislike") {
            setFive()
        } else if (setName == "Family Members") {
            setSix()
        } else if (setName == "Frequency & Time") {
            setSeven()
        } else if (setName == "Form questions") {
            setEight()
        } else if (setName == "Request Service") {
            setNine()
        } else if (setName == "Expressions") {
            setTen()
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
                val intent = Intent(this@QuestionActivity3, ScoreActivity::class.java)
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
                val dialog = Dialog(this@QuestionActivity3)
                dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.timeout_dialog)
                dialog.findViewById<View>(R.id.tryAgain).setOnClickListener {
                    val intent = Intent(this@QuestionActivity3, SetsActivity2::class.java)
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
        list.add(QuestionModel("How do you say 'hello' in Spanish?", "A. hola", "B. adiós", "C. gracias", "D. por favor", "A. hola"))
        list.add(QuestionModel("What does 'adiós' mean in English?", "A. hello", "B. goodbye", "C. thank you", "D. please", "B. goodbye"))
        list.add(QuestionModel("What is the translation of '¿Cómo estás?' into English?", "A. How are you?", "B. What's your name?", "C. Where are you from?", "D. Nice to meet you.", "A. How are you?"))
        list.add(QuestionModel("How do you say 'My name is John' in Spanish?", "A. Mi nombre es John.", "B. Hola, John.", "C. ¿Quién es John?", "D. Buenos días, John.", "A. Mi nombre es John."))
        list.add(QuestionModel("What does 'gracias' mean in English?", "A. hello", "B. goodbye", "C. thank you", "D. please", "C. thank you"))
    }

    private fun setTwo() {
        list.add(QuestionModel("What is the Spanish word for 'food'?", "A. comida", "B. agua", "C. leche", "D. fruta", "A. comida"))
        list.add(QuestionModel("How do you say 'water' in Spanish?", "A. té", "B. café", "C. agua", "D. leche", "C. agua"))
        list.add(QuestionModel("What does 'fruta' mean in English?", "A. fruit", "B. vegetable", "C. meat", "D. rice", "A. fruit"))
        list.add(QuestionModel("How do you write 'rice' in Spanish?", "A. arroz", "B. pollo", "C. carne", "D. pescado", "A. arroz"))
        list.add(QuestionModel("Which of the following means 'drink' in Spanish?", "A. comer", "B. beber", "C. cocinar", "D. dormir", "B. beber"))
    }
    private fun setThree() {
        list.add(QuestionModel("What is the Spanish word for 'Chinese'?", "A. chino", "B. japonés", "C. alemán", "D. francés", "A. chino"))
        list.add(QuestionModel("How do you say 'American' in Spanish?", "A. americano", "B. británico", "C. japonés", "D. francés", "A. americano"))
        list.add(QuestionModel("What does 'francés' (French) mean in Spanish?", "A. Chinese", "B. American", "C. Japanese", "D. French", "D. French"))
        list.add(QuestionModel("How do you write 'Japanese' in Spanish?", "A. japonés", "B. chino", "C. coreano", "D. inglés", "A. japonés"))
        list.add(QuestionModel("Which of the following means 'German' in Spanish?", "A. alemán", "B. italiano", "C. holandés", "D. sueco", "A. alemán"))
    }

    private fun setFour() {
        list.add(QuestionModel("What is the Spanish word for 'teacher'?", "A. maestro", "B. doctor", "C. ingeniero", "D. abogado", "A. maestro"))
        list.add(QuestionModel("How do you say 'doctor' in Spanish?", "A. profesor", "B. médico", "C. dentista", "D. enfermero", "B. médico"))
        list.add(QuestionModel("What does 'ingeniero' (engineer) mean in Spanish?", "A. engineer", "B. teacher", "C. doctor", "D. lawyer", "A. engineer"))
        list.add(QuestionModel("How do you write 'lawyer' in Spanish?", "A. abogado", "B. juez", "C. policía", "D. detective", "A. abogado"))
        list.add(QuestionModel("Which of the following means 'engineer' in Spanish?", "A. ingeniero", "B. arquitecto", "C. constructor", "D. electricista", "A. ingeniero"))
    }

    private fun setFive() {
        list.add(QuestionModel("How do you say 'I like' in Spanish?", "A. me gusta", "B. no me gusta", "C. quiero", "D. amo", "A. me gusta"))
        list.add(QuestionModel("Which phrase means 'I don't like' in Spanish?", "A. me gusta", "B. no me gusta", "C. quiero", "D. amo", "B. no me gusta"))
        list.add(QuestionModel("What does 'quiero' mean in Spanish?", "A. I like", "B. I don't like", "C. I want", "D. I love", "C. I want"))
        list.add(QuestionModel("How do you write 'I love' in Spanish?", "A. me gusta", "B. no me gusta", "C. amo", "D. quiero", "C. amo"))
        list.add(QuestionModel("Which phrase means 'I prefer' in Spanish?", "A. me gusta", "B. no me gusta", "C. prefiero", "D. quiero", "C. prefiero"))
    }

    private fun setSix() {
        list.add(QuestionModel("What is the Spanish word for 'father'?", "A. padre", "B. madre", "C. hermano", "D. hermana", "A. padre"))
        list.add(QuestionModel("How do you say 'mother' in Spanish?", "A. padre", "B. madre", "C. hermano", "D. hermana", "B. madre"))
        list.add(QuestionModel("What does 'hermana' mean in Spanish?", "A. father", "B. mother", "C. brother", "D. sister", "D. sister"))
        list.add(QuestionModel("How do you write 'grandmother' in Spanish?", "A. abuela", "B. abuelo", "C. tía", "D. tío", "A. abuela"))
        list.add(QuestionModel("Which of the following means 'uncle' in Spanish?", "A. primo", "B. sobrino", "C. tío", "D. hermano", "C. tío"))
    }

    private fun setSeven() {
        list.add(QuestionModel("What is the Spanish adverb for 'always'?", "A. nunca", "B. siempre", "C. a veces", "D. casi nunca", "B. siempre"))
        list.add(QuestionModel("How do you say 'often' in Spanish?", "A. raramente", "B. a veces", "C. frecuentemente", "D. siempre", "C. frecuentemente"))
        list.add(QuestionModel("What does 'nunca' mean in Spanish?", "A. sometimes", "B. often", "C. never", "D. always", "C. never"))
        list.add(QuestionModel("How do you write 'rarely' in Spanish?", "A. a veces", "B. nunca", "C. frecuentemente", "D. raramente", "D. raramente"))
        list.add(QuestionModel("Which of the following means 'sometimes' in Spanish?", "A. nunca", "B. raramente", "C. a veces", "D. frecuentemente", "C. a veces"))
    }

    private fun setEight() {
        list.add(QuestionModel("How do you form a yes/no question in Spanish?", "A. By adding 'sí' at the beginning of the statement", "B. By adding 'no' at the beginning of the statement", "C. By adding 'qué' at the beginning of the statement", "D. By adding 'dónde' at the beginning of the statement", "A. By adding 'sí' at the beginning of the statement"))
        list.add(QuestionModel("Which of the following is used to form a question asking 'what?' in Spanish?", "A. cómo", "B. qué", "C. quién", "D. cuándo", "B. qué"))
        list.add(QuestionModel("What does 'dónde' mean in Spanish?", "A. when", "B. who", "C. what", "D. where", "D. where"))
        list.add(QuestionModel("How do you say 'who' in Spanish?", "A. quién", "B. qué", "C. cuándo", "D. dónde", "A. quién"))
        list.add(QuestionModel("Which of the following means 'when' in Spanish?", "A. quién", "B. qué", "C. cuándo", "D. dónde", "C. cuándo"))
    }

    private fun setNine() {
        list.add(QuestionModel("What is the Spanish word for 'cat'?", "A. gato", "B. perro", "C. pájaro", "D. ratón", "A. gato"))
        list.add(QuestionModel("How do you say 'dog' in Spanish?", "A. gato", "B. perro", "C. pájaro", "D. ratón", "B. perro"))
        list.add(QuestionModel("What does 'pájaro' mean in Spanish?", "A. cat", "B. dog", "C. bird", "D. mouse", "C. bird"))
        list.add(QuestionModel("How do you write 'mouse' in Spanish?", "A. gato", "B. perro", "C. pájaro", "D. ratón", "D. ratón"))
        list.add(QuestionModel("Which of the following means 'bird' in Spanish?", "A. gato", "B. perro", "C. pájaro", "D. ratón", "C. pájaro"))
    }

    private fun setTen() {
        list.add(QuestionModel("How do you say 'big' in Spanish?", "A. grande", "B. pequeño", "C. alto", "D. bajo", "A. grande"))
        list.add(QuestionModel("Which of the following means 'small' in Spanish?", "A. grande", "B. pequeño", "C. alto", "D. bajo", "B. pequeño"))
        list.add(QuestionModel("What does 'alto' mean in Spanish?", "A. big", "B. small", "C. tall", "D. short", "C. tall"))
        list.add(QuestionModel("How do you write 'short' in Spanish?", "A. grande", "B. pequeño", "C. alto", "D. bajo", "D. bajo"))
        list.add(QuestionModel("Which of the following means 'tall' in Spanish?", "A. grande", "B. pequeño", "C. alto", "D. bajo", "C. alto"))
    }

}