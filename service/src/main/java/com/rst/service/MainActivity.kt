package com.rst.service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.startServiceButton).setOnClickListener {
            startService(Intent(this, PlayService::class.java))
        }

        findViewById<Button>(R.id.stopServiceButton).setOnClickListener {
            stopService(Intent(this, PlayService::class.java))
        }

        findViewById<Button>(R.id.startIntentServiceButton).setOnClickListener {
            MyIntentService.startActionBaz(this, "a", "b")
        }
    }
}