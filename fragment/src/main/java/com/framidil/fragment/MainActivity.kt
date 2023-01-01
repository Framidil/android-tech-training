package com.framidil.fragment

import android.content.Intent
import android.os.Bundle
import android.os.HandlerThread
import android.os.Looper
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.framidil.fragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.myText.text = "Portrait TextView"
        binding.myIncludes.myButton.setOnClickListener {
            showToast(Thread.currentThread().name + ", " + mainLooper.toString())
            Thread {
                Looper.prepare()
                val looper = Looper.myLooper()

                runOnUiThread {
                    showToast(looper.toString())
                }
            }.start()

            val thread = HandlerThread("thread")
            thread.start()
        }

        binding.firstFragmentActivity.setOnClickListener {
            startActivity(Intent(this, FirstFragmentActivity::class.java))
        }
        startActivity(Intent(this, FirstFragmentActivity::class.java))

    }

    private fun showToast(s: String) {
        val toast = Toast.makeText(
            applicationContext,
            "Пора покормить кота!",
            Toast.LENGTH_SHORT
        )

        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}