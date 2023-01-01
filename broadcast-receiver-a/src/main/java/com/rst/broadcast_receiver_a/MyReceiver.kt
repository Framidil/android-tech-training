package com.rst.broadcast_receiver_a

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("TAG", "onReceive")
        Toast.makeText(
            context, "Обнаружено сообщение: " +
                    intent.getStringExtra("ru.alexanderklimov.broadcast.Message"),
            Toast.LENGTH_LONG
        ).show()
    }
}