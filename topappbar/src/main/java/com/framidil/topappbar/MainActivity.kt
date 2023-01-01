package com.framidil.topappbar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.framidil.topappbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var appBarIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changeAppBarButton.setOnClickListener {
            appBarIndex++
            changeAppBar()
        }

        binding.toAppBarActivityButton.setOnClickListener {
            startActivity(Intent(this, AppBarActivity::class.java))
        }
    }

    private fun changeAppBar() {
    }
}