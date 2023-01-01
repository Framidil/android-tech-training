package com.framidil.styleapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.framidil.styleapp.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private var resultInt = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setResultButton.setOnClickListener {
            binding.resultTextView.text = resultInt.toString()

            setResult(1, Intent().putExtra("resultInt", resultInt))
            resultInt++
        }
    }
}