package com.rst.broadcast_receiver_b

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private lateinit var myReceiver: MyReceiver
    private lateinit var myChargeReceiver: MyChargeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(MyReceiver().also { myReceiver = it }, IntentFilter().apply {
            addAction("com.rst.broadcast_receiver_b.test")
        })

        registerReceiver(MyChargeReceiver().also { myChargeReceiver = it }, IntentFilter().apply {
            addAction("android.intent.action.ACTION_POWER_CONNECTED")
        })
    }

    override fun onDestroy() {
        unregisterReceiver(myReceiver)
        unregisterReceiver(myChargeReceiver)
        super.onDestroy()
    }
}