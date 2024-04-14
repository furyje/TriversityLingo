package com.example.triversitylingo2.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.triversitylingo2.Adapters.SetAdapter
import com.example.triversitylingo2.Models.SetModel
import com.example.triversitylingo2.databinding.ActivitySetsBinding

class SetsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetsBinding
    private val list: ArrayList<SetModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val linearLayoutManager = LinearLayoutManager(this)
        binding.setRecy.layoutManager = linearLayoutManager

        list.add(SetModel("Greetings"))
        list.add(SetModel("Food & Beverages"))
        list.add(SetModel("Nationalities"))
        list.add(SetModel("Occupations"))
        list.add(SetModel("Like and Dislike"))
        list.add(SetModel("Family Members"))
        list.add(SetModel("Frequency & Time"))
        list.add(SetModel("Form questions"))
        list.add(SetModel("Request Service"))
        list.add(SetModel("Expressions"))

        val adapter = SetAdapter(this, list)
        binding.setRecy.adapter = adapter

        // Inside the click listener for your RecyclerView items
        adapter.setOnItemClickListener { setModel ->
            val intent = Intent(this@SetsActivity, QuestionActivity::class.java)
            intent.putExtra("setName", setModel.setName) // Pass the set name to QuestionActivity
            startActivity(intent)
        }

    }
}
