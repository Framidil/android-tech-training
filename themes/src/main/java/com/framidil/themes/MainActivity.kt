package com.framidil.themes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.framidil.themes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView1.setOnClickListener {
//            AlertDialog.Builder(this).setView(layoutInflater.inflate(R.layout.activity_second, null)).create().show()
            startActivity(Intent(this, SecondActivity::class.java))
        }

        SecondActivity.A
    }
}