package com.rst.logcat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val l = Logger.getLogger("MyLogger")
        lifecycleScope.launch {
            while (true) {
                Timber.d("Log A")
                Timber.tag("B").d("Log B")

                l.log(Level.INFO, "MyLogger A!")
                Timber.tag("WTF").wtf("A?")
                delay(TimeUnit.SECONDS.toMillis(1))
                F().b()
            }
        }
    }
}

class F {
    fun b() {
        a()
    }

    private fun a() {
        Timber.d("A?")
    }
}