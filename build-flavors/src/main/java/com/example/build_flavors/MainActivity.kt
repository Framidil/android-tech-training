package com.example.build_flavors

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            while (true) {
                delay(300)
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                Log.d("tag", "isActive on view: ${imm.isActive(this)}")
                Log.d("tag", "isActive: ${imm.isActive}")
            }
        }
    }
}