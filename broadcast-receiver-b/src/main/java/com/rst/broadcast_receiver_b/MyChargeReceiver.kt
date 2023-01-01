package com.rst.broadcast_receiver_b

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyChargeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("CHARGE!", "onReceive!")
        context.startActivity(Intent(context, MainActivity::class.java))
        Toast.makeText(context, "MyChargeReceiver in broadcast-receiver-b!", Toast.LENGTH_SHORT).show()

    }
}