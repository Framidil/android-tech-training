package com.rst.broadcast_receiver_b

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("TAG2", "onReceive broadcast-receiver-b")
        Toast.makeText(context, "onReceive in broadcast-receiver-b!", Toast.LENGTH_SHORT).show()
    }
}