package com.rst.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.widget.Toast

private const val ACTION_FOO = "com.rst.service.action.FOO"
private const val ACTION_BAZ = "com.rst.service.action.BAZ"

private const val EXTRA_PARAM1 = "com.rst.service.extra.PARAM1"
private const val EXTRA_PARAM2 = "com.rst.service.extra.PARAM2"

class MyIntentService : IntentService("MyIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_FOO -> {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)
                handleActionFoo(param1, param2)
            }
            ACTION_BAZ -> {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)
                handleActionBaz(param1, param2)
            }
        }
    }

    private fun handleActionFoo(param1: String?, param2: String?) {
        Toast.makeText(this, "handleActionFoo $param1 $param2", Toast.LENGTH_SHORT).show()
    }

    private fun handleActionBaz(param1: String?, param2: String?) {
        Toast.makeText(this, "handleActionBaz $param1 $param2", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun startActionFoo(context: Context, param1: String, param2: String) {
            val intent = Intent(context, MyIntentService::class.java).apply {
                action = ACTION_FOO
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            context.startService(intent)
        }

        fun startActionBaz(context: Context, param1: String, param2: String) {
            val intent = Intent(context, MyIntentService::class.java).apply {
                action = ACTION_BAZ
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            context.startService(intent)
        }
    }
}