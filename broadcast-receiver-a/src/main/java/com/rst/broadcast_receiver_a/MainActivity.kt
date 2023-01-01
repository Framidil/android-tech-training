package com.rst.broadcast_receiver_a

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.rst.broadcast_receiver_a.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val MY_ACTION = "ru.alexanderklimov.action.CAT"
    val ALARM_MESSAGE = "Срочно пришлите кота!"

    lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createBroadCastReceiver()

        binding.sendButton1.setOnClickListener {
            sendImplicitBroadcast()
        }

        binding.sendButton2.setOnClickListener {
            sendExplicitBroadcast()
        }

        binding.sendButton3.setOnClickListener {
            sendBroadcastToAnotherApp()
        }
    }

    private fun sendImplicitBroadcast() {
        val intent = Intent().apply {
            action = MY_ACTION
            putExtra("ru.alexanderklimov.broadcast.Message", "implicit sendButton2")
            addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
        }
        sendBroadcast(intent)
        Log.d("TAG", "implicit sended")
    }

    private fun sendExplicitBroadcast() {
        val intent = Intent(this, MyReceiver::class.java).apply {
            putExtra("ru.alexanderklimov.broadcast.Message", "explicit sendButton1")
            addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
        }
        sendBroadcast(intent)
        Log.d("TAG", "explicit sended")
    }

    private fun sendBroadcastToAnotherApp() {
        sendBroadcast(Intent().apply {
            action = "com.rst.broadcast_receiver_b.test"
            addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
        })
    }

    private fun createBroadCastReceiver() {
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("TAG", "onReceive")
                Toast.makeText(
                    context, "Обнаружено сообщение: " +
                            intent?.getStringExtra("ru.alexanderklimov.broadcast.Message"),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_PAUSE) {
                    unregisterReceiver(receiver)
                } else if (event == Lifecycle.Event.ON_RESUME) {
                    registerReceiver(receiver, IntentFilter().apply {
                        addAction(MY_ACTION)
                        addAction(Intent.ACTION_TIME_TICK)
                    })
                }
            }
        })
    }
}