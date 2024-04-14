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

class QuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionBinding
    private val list = ArrayList<QuestionModel>()
    private var count = 0
    private var position = 0
    private var score = 0
    private lateinit var timer: CountDownTimer
    private lateinit var btnShare: Button


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
        btnShare = findViewById(R.id.btnShare)



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
                val intent = Intent(this@QuestionActivity, ScoreActivity::class.java)
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
                val dialog = Dialog(this@QuestionActivity)
                dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.timeout_dialog)
                dialog.findViewById<View>(R.id.tryAgain).setOnClickListener {
                    val intent = Intent(this@QuestionActivity, SetsActivity::class.java)
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
        list.add(QuestionModel("How do you say 'name' in Mandarin?", "A. 名字 (Míngzi) - meeng dzuh", "B. 你好 (Nǐ hǎo) - nee how", "C. 谢谢 (Xièxiè) - syeh-syeh", "D. 对不起 (Duìbùqǐ) - dway boo chee", "A. 名字 (Míngzi) - meeng dzuh"))
        list.add(QuestionModel("Which character represents the word 'name' in Mandarin?", "A. 名字 (Míngzi) - meeng dzuh", "B. 你好 (Nǐ hǎo) - nee how", "C. 好吃 (Hǎochī) - how chur", "D. 早上好 (Zǎoshang hǎo) - zaow shang how", "Answer: A. 名字 (Míngzi) - Name"))
        list.add(QuestionModel("What does '你好' (Nǐ hǎo) mean in English?", "A. Thank you", "B. Hello", "C. Goodbye", "D. Sorry", "B. Hello"))
        list.add(QuestionModel("How do you say 'my name is' in Mandarin?", "A. 我的名字是 (Wǒ de míngzi shì) - wo de meeng dzuh shr", "B. 是 (Shì) - shr", "C. 否 (Fǒu) - foo", "D. 没有 (Méiyǒu) - may-yo", "Answer: A. 我的名字是 (Wǒ de míngzi shì) - My name is"))
        list.add(QuestionModel("What does '对不起' (Duìbùqǐ) mean in English?", "A. Excuse me", "B. Yes", "C. Sorry", "D. Goodbye", "C. Sorry"))
    }


    private fun setTwo() {
        list.add(QuestionModel("What is the Mandarin word for 'food'?", "A. 食品 (Shípǐn) - shir peen", "B. 水果 (Shuǐguǒ) - shway gwoh", "C. 食物 (Shíwù) - shir woo", "D. 饮料 (Yǐnliào) - yin lee-ow", "C. 食物 (Shíwù) - food"))
        list.add(QuestionModel("How do you say 'water' in Mandarin?", "A. 茶 (Chá) - chah", "B. 牛奶 (Niúnǎi) - nyoh-nigh", "C. 水 (Shuǐ) - shway", "D. 饮料 (Yǐnliào) - yin lee-ow", "C. 水 (Shuǐ) - water"))
        list.add(QuestionModel("What does '水果' (Shuǐguǒ) mean in English?", "A. Vegetable", "B. Fruit", "C. Meat", "D. Rice", "B. Fruit"))
        list.add(QuestionModel("How do you write 'rice' in Mandarin?", "A. 米饭 (Mǐfàn) - mee fan", "B. 面条 (Miàntiáo) - myen tyow", "C. 面包 (Miànbāo) - myen bao", "D. 水果 (Shuǐguǒ) - shway gwoh", "A. 米饭 (Mǐfàn) - rice"))
        list.add(QuestionModel("Which of the following means 'drink' in Mandarin?", "A. 吃 (Chī) - chir", "B. 喝 (Hē) - her", "C. 烤 (Kǎo) - kow", "D. 煮 (Zhǔ) - joo", "B. 喝 (Hē) - drink"))
    }


    private fun setThree() {
        list.add(QuestionModel("What is the Mandarin word for 'Chinese'?", "A. 英国人 (Yīngguórén) - ying gwuh ren", "B. 法国人 (Fǎguórén) - fah gwuh ren", "C. 中国人 (Zhōngguórén) - jong gwuh ren", "D. 德国人 (Déguórén) - duh gwuh ren", "C. 中国人 (Zhōngguórén) - Chinese"))
        list.add(QuestionModel("How do you say 'American' in Mandarin?", "A. 日本人 (Rìběnrén) - ri ben ren", "B. 美国人 (Měiguórén) - may gwuh ren", "C. 加拿大人 (Jiānádàrén) - jyaa na da ren", "D. 英国人 (Yīngguórén) - ying gwuh ren", "B. 美国人 (Měiguórén) - American"))
        list.add(QuestionModel("What does '法国人' (Fǎguórén) mean in English?", "A. Chinese", "B. Japanese", "C. French", "D. German", "C. French"))
        list.add(QuestionModel("How do you write 'Japanese' in Mandarin?", "A. 日本人 (Rìběnrén) - ri ben ren", "B. 美国人 (Měiguórén) - may gwuh ren", "C. 中国人 (Zhōngguórén) - jong gwuh ren", "D. 英国人 (Yīngguórén) - ying gwuh ren", "A. 日本人 (Rìběnrén) - Japanese"))
        list.add(QuestionModel("Which of the following means 'German' in Mandarin?", "A. 德国人 (Déguórén) - duh gwuh ren", "B. 法国人 (Fǎguórén) - fah gwuh ren", "C. 加拿大人 (Jiānádàrén) - jyaa na da ren", "D. 韩国人 (Hánguórén) - han gwuh ren", "A. 德国人 (Déguórén) - German"))
    }


    private fun setFour() {
        list.add(QuestionModel("What is the Mandarin word for 'teacher'?", "A. 医生 (Yīshēng) - yee shung", "B. 老师 (Lǎoshī) - lao shir", "C. 工程师 (Gōngchéngshī) - gong chung shir", "D. 商人 (Shāngrén) - shang ren", "B. 老师 (Lǎoshī) - teacher"))
        list.add(QuestionModel("How do you say 'doctor' in Mandarin?", "A. 老师 (Lǎoshī) - lao shir", "B. 医生 (Yīshēng) - yee shung", "C. 工程师 (Gōngchéngshī) - gong chung shir", "D. 商人 (Shāngrén) - shang ren", "B. 医生 (Yīshēng) - doctor"))
        list.add(QuestionModel("What does '工程师' (Gōngchéngshī) mean in English?", "A. Engineer", "B. Teacher", "C. Doctor", "D. Lawyer", "A. Engineer"))
        list.add(QuestionModel("How do you write 'lawyer' in Mandarin?", "A. 律师 (Lǜshī) - lü shir", "B. 老师 (Lǎoshī) - lao shir", "C. 医生 (Yīshēng) - yee shung", "D. 工程师 (Gōngchéngshī) - gong chung shir", "A. 律师 (Lǜshī) - lawyer"))
        list.add(QuestionModel("Which of the following means 'engineer' in Mandarin?", "A. 工程师 (Gōngchéngshī) - gong chung shir", "B. 商人 (Shāngrén) - shang ren", "C. 老师 (Lǎoshī) - lao shir", "D. 律师 (Lǜshī) - lü shir", "A. 工程师 (Gōngchéngshī) - engineer"))
    }


    private fun setFive() {
        list.add(QuestionModel("How do you say 'I like' in Mandarin?", "A. 我喜欢 (Wǒ xǐhuān) - woh shee-hwan", "B. 我不喜欢 (Wǒ bù xǐhuān) - woh boo shee-hwan", "C. 我要 (Wǒ yào) - woh yow", "D. 我吃 (Wǒ chī) - woh chir", "A. 我喜欢 (Wǒ xǐhuān) - I like"))
        list.add(QuestionModel("Which phrase means 'I don't like' in Mandarin?", "A. 我喜欢 (Wǒ xǐhuān) - woh shee-hwan", "B. 我不喜欢 (Wǒ bù xǐhuān) - woh boo shee-hwan", "C. 我要 (Wǒ yào) - woh yow", "D. 我吃 (Wǒ chī) - woh chir", "B. 我不喜欢 (Wǒ bù xǐhuān) - I don't like"))
        list.add(QuestionModel("What does '我要' (Wǒ yào) mean in English?", "A. I like", "B. I don't like", "C. I want", "D. I eat", "C. I want"))
        list.add(QuestionModel("How do you write 'I love' in Mandarin?", "A. 我爱 (Wǒ ài) - woh eye", "B. 我不喜欢 (Wǒ bù xǐhuān) - woh boo shee-hwan", "C. 我吃 (Wǒ chī) - woh chir", "D. 我要 (Wǒ yào) - woh yow", "A. 我爱 (Wǒ ài) - I love"))
        list.add(QuestionModel("Which phrase means 'I prefer' in Mandarin?", "A. 我喜欢 (Wǒ xǐhuān) - woh shee-hwan", "B. 我比较喜欢 (Wǒ bǐjiào xǐhuān) - woh bee-jyow shee-hwan", "C. 我要 (Wǒ yào) - woh yow", "D. 我吃 (Wǒ chī) - woh chir", "B. 我比较喜欢 (Wǒ bǐjiào xǐhuān) - I prefer"))
    }
    private fun setSix() {
        list.add(QuestionModel("What is the Mandarin word for 'father'?", "A. 妈妈 (Māma) - mah-mah", "B. 爸爸 (Bàba) - bah-bah", "C. 哥哥 (Gēge) - geh-geh", "D. 妹妹 (Mèimei) - may-may", "B. 爸爸 (Bàba) - father"))
        list.add(QuestionModel("How do you say 'mother' in Mandarin?", "A. 爸爸 (Bàba) - bah-bah", "B. 妈妈 (Māma) - mah-mah", "C. 哥哥 (Gēge) - geh-geh", "D. 妹妹 (Mèimei) - may-may", "B. 妈妈 (Māma) - mother"))
        list.add(QuestionModel("What does '姐姐' (Jiějie) mean in English?", "A. Father", "B. Mother", "C. Older sister", "D. Younger sister", "C. Older sister"))
        list.add(QuestionModel("How do you write 'grandmother' in Mandarin?", "A. 奶奶 (Nǎinai) - nye-nye", "B. 爷爷 (Yéye) - yeh-yeh", "C. 妈妈 (Māma) - mah-mah", "D. 姐姐 (Jiějie) - jyeah-jyeah", "A. 奶奶 (Nǎinai) - grandmother"))
        list.add(QuestionModel("Which of the following means 'uncle' in Mandarin?", "A. 爸爸 (Bàba) - bah-bah", "B. 叔叔 (Shūshu) - shoo-shoo", "C. 爷爷 (Yéye) - yeh-yeh", "D. 哥哥 (Gēge) - geh-geh", "B. 叔叔 (Shūshu) - uncle"))
    }
    private fun setSeven() {
        list.add(QuestionModel("What is the Mandarin adverb for 'always'?", "A. 有时候 (Yǒu shíhou) - yo shur-hoh", "B. 从来 (Cónglái) - chong-lie", "C. 经常 (Jīngcháng) - jing-chung", "D. 总是 (Zǒngshì) - zong-shur", "D. 总是 (Zǒngshì) - always"))
        list.add(QuestionModel("How do you say 'often' in Mandarin?", "A. 很少 (Hěn shǎo) - hun shaow", "B. 有时候 (Yǒu shíhou) - yo shur-hoh", "C. 经常 (Jīngcháng) - jing-chung", "D. 偶尔 (Ǒu'ěr) - oh-ur", "C. 经常 (Jīngcháng) - often"))
        list.add(QuestionModel("What does '从来' (Cónglái) mean in English?", "A. Sometimes", "B. Often", "C. Never", "D. Always", "C. Never"))
        list.add(QuestionModel("How do you write 'rarely' in Mandarin?", "A. 有时候 (Yǒu shíhou) - yo shur-hoh", "B. 从来 (Cónglái) - chong-lie", "C. 很少 (Hěn shǎo) - hun shaow", "D. 几乎不 (Jīhū bù) - jee-hoo boo", "C. 很少 (Hěn shǎo) - rarely"))
        list.add(QuestionModel("Which of the following means 'sometimes' in Mandarin?", "A. 有时候 (Yǒu shíhou) - yo shur-hoh", "B. 从来 (Cónglái) - chong-lie", "C. 总是 (Zǒngshì) - zong-shur", "D. 经常 (Jīngcháng) - jing-chung", "A. 有时候 (Yǒu shíhou) - sometimes"))
    }
    private fun setEight() {
        list.add(QuestionModel("How do you form a yes/no question in Mandarin?", "A. By adding '吗 (ma)' at the end of the statement", "B. By adding '不 (bù)' at the beginning of the statement", "C. By adding '什么 (shénme)' at the beginning of the statement", "D. By adding '谁 (shéi)' at the beginning of the statement", "A. By adding '吗 (ma)' at the end of the statement"))
        list.add(QuestionModel("Which of the following is used to form a question asking 'what?' in Mandarin?", "A. 什么 (shénme)", "B. 吗 (ma)", "C. 谁 (shéi)", "D. 哪里 (nǎlǐ)", "A. 什么 (shénme)"))
        list.add(QuestionModel("How do you ask 'Where are you from?' in Mandarin?", "A. 你姓什么？ (Nǐ xìng shénme?)", "B. 你是哪国人？ (Nǐ shì nǎguó rén?)", "C. 你叫什么名字？ (Nǐ jiào shénme míngzi?)", "D. 你多大了？ (Nǐ duō dà le?)", "B. 你是哪国人？ (Nǐ shì nǎguó rén?)"))
        list.add(QuestionModel("How do you ask 'How are you?' in Mandarin?", "A. 你好吗？ (Nǐ hǎo ma?)", "B. 你是谁？ (Nǐ shì shéi?)", "C. 你在哪里？ (Nǐ zài nǎlǐ?)", "D. 你想做什么？ (Nǐ xiǎng zuò shénme?)", "A. 你好吗？ (Nǐ hǎo ma?)"))
        list.add(QuestionModel("Which of the following is used to form a question asking 'who?' in Mandarin?", "A. 什么 (shénme)", "B. 吗 (ma)", "C. 谁 (shéi)", "D. 哪里 (nǎlǐ)", "C. 谁 (shéi)"))
    }
    private fun setNine() {
        list.add(QuestionModel("How do you ask 'Where is the bathroom?' in Mandarin?", "A. 这是什么？ (Zhè shì shénme?)", "B. 请问，洗手间在哪里？ (Qǐngwèn, xǐshǒujiān zài nǎlǐ?)", "C. 我想买票。 (Wǒ xiǎng mǎi piào.)", "D. 我要一杯咖啡。 (Wǒ yào yī bēi kāfēi.)", "B. 请问，洗手间在哪里？ (Qǐngwèn, xǐshǒujiān zài nǎlǐ?)"))
        list.add(QuestionModel("How do you say 'Excuse me, where is the train station?' in Mandarin?", "A. 对不起，我不懂中文。 (Duìbuqǐ, wǒ bù dǒng zhōngwén.)", "B. 请问，地铁站在哪里？ (Qǐngwèn, dìtiězhàn zài nǎlǐ?)", "C. 请问，火车站在哪里？ (Qǐngwèn, huǒchēzhàn zài nǎlǐ?)", "D. 我想去这个地方。 (Wǒ xiǎng qù zhège dìfāng.)", "C. 请问，火车站在哪里？ (Qǐngwèn, huǒchēzhàn zài nǎlǐ?)"))
        list.add(QuestionModel("What does '一张地图' (Yī zhāng dìtú) mean in English?", "A. A ticket", "B. A map", "C. A hotel", "D. A taxi", "B. A map"))
        list.add(QuestionModel("How do you ask 'How much is this?' in Mandarin?", "A. 多少钱？ (Duōshǎo qián?)", "B. 你好吗？ (Nǐ hǎo ma?)", "C. 这是什么？ (Zhè shì shénme?)", "D. 我要买一张地图。 (Wǒ yào mǎi yī zhāng dìtú.)", "A. 多少钱？ (Duōshǎo qián?)"))
        list.add(QuestionModel("How do you say 'I would like a taxi, please' in Mandarin?", "A. 我要一杯咖啡。 (Wǒ yào yī bēi kāfēi.)", "B. 我想去这个地方。 (Wǒ xiǎng qù zhège dìfāng.)", "C. 我要买票。 (Wǒ yào mǎi piào.)", "D. 我要一辆出租车，谢谢。 (Wǒ yào yī liàng chūzūchē, xièxiè.)", "D. 我要一辆出租车，谢谢。 (Wǒ yào yī liàng chūzūchē, xièxiè.)"))
    }
    private fun setTen() {
        list.add(QuestionModel("What is the Mandarin measure word for 'cup'?", "A. 杯 (Bēi)", "B. 个 (Gè)", "C. 包 (Bāo)", "D. 件 (Jiàn)", "A. 杯 (Bēi)"))
        list.add(QuestionModel("How do you say 'I want to meet my friend tomorrow' in Mandarin?", "A. 我要去看电影。 (Wǒ yào qù kàn diànyǐng.)", "B. 我要去吃饭。 (Wǒ yào qù chīfàn.)", "C. 明天我想见朋友。 (Míngtiān wǒ xiǎng jiàn péngyǒu.)", "D. 我不想去。 (Wǒ bùxiǎng qù.)", "C. 明天我想见朋友。 (Míngtiān wǒ xiǎng jiàn péngyǒu.)"))
        list.add(QuestionModel("What does '一件事情' (Yī jiàn shìqíng) mean in English?", "A. One thing", "B. Two things", "C. Three things", "D. Four things", "A. One thing"))
        list.add(QuestionModel("How do you ask 'Do you want to go shopping together?' in Mandarin?", "A. 你想去游泳吗？ (Nǐ xiǎng qù yóuyǒng ma?)", "B. 你想和我一起去逛街吗？ (Nǐ xiǎng hé wǒ yīqǐ qù guàngjiē ma?)", "C. 你想去看电影吗？ (Nǐ xiǎng qù kàn diànyǐng ma?)", "D. 你想吃饭吗？ (Nǐ xiǎng chīfàn ma?)", "B. 你想和我一起去逛街吗？ (Nǐ xiǎng hé wǒ yīqǐ qù guàngjiē ma?)"))
        list.add(QuestionModel("How do you say 'Let's meet at 2 o'clock' in Mandarin?", "A. 我们在两点见面。 (Wǒmen zài liǎng diǎn jiànmiàn.)", "B. 我们在一点见面。 (Wǒmen zài yī diǎn jiànmiàn.)", "C. 我们在三点见面。 (Wǒmen zài sān diǎn jiànmiàn.)", "D. 我们在四点见面。 (Wǒmen zài sì diǎn jiànmiàn.)", "A. 我们在两点见面。 (Wǒmen zài liǎng diǎn jiànmiàn.)"))
    }





}