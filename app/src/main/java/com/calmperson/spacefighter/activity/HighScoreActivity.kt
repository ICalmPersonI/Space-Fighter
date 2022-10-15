package com.calmperson.spacefighter.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.calmperson.spacefighter.databinding.ActivityHighScoreBinding

class HighScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHighScoreBinding
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHighScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("SHAR_PREF_NAME", Context.MODE_PRIVATE)

        binding.firstPlace.text = "1. " + sharedPreferences.getInt("score1", 0).toString()
        binding.secondPlace.text = "2. " + sharedPreferences.getInt("score2", 0).toString()
        binding.thirdPlace.text = "3. " + sharedPreferences.getInt("score3", 0).toString()
        binding.fourthPlace.text = "4. " + sharedPreferences.getInt("score4", 0).toString()
    }
}