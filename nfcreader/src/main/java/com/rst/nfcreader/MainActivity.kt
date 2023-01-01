package com.rst.nfcreader

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.*
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: NfcAdapter

    fun nfcWork() {
        val mNFCAdapter = NfcAdapter.getDefaultAdapter(this)

        if (mNFCAdapter == null) {
            // Device does not support NFC
            Toast.makeText(
                this,
                "mNFCAdapter == null",
                Toast.LENGTH_LONG
            ).show()
        } else {
            if (!mNFCAdapter.isEnabled) {
                // NFC is disabled
                Toast.makeText(
                    this, "NFC is disabled",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this, "NFC WORK!",
                    Toast.LENGTH_LONG
                ).show()
                val mNfcPendingIntent = PendingIntent.getActivity(
                    this,
                    0, Intent(this, MainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), FLAG_IMMUTABLE
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(
            applicationContext,
            "onCreate",
            Toast.LENGTH_LONG
        ).show()

        adapter = NfcAdapter.getDefaultAdapter(this)!!
    }

    public override fun onResume() {
        super.onResume()
        val intentFiltersArray = arrayOf(
            IntentFilter("android.nfc.action.TAG_DISCOVERED")
        )

        val techListsArray = arrayOf(
            arrayOf(
                NfcF::class.java.name,
                IsoDep::class.java.name,
                NfcA::class.java.name,
                NfcB::class.java.name,
                NfcF::class.java.name,
                NfcV::class.java.name,
                Ndef::class.java.name,
                NdefFormatable::class.java.name,
                MifareClassic::class.java.name,
                MifareUltralight::class.java.name,
            )
        )

        val intent = Intent(this, javaClass)
        val pendingIntent = PendingIntent.getActivity(this, 1, intent, FLAG_IMMUTABLE)
        adapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, null)
    }

    public override fun onPause() {
        super.onPause()
        adapter.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent1: Intent?) {
        super.onNewIntent(intent1)
        Toast.makeText(
            applicationContext,
            "NFC1",
            Toast.LENGTH_LONG
        ).show()
        val action = intent1?.action
        var msg = "intent.toString()): ${intent1?.toString()}\n"
        msg += "intent.action: ${action}\n"
        if (action.equals(NfcAdapter.ACTION_TAG_DISCOVERED) ||
            action.equals(NfcAdapter.ACTION_TECH_DISCOVERED) ||
            action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)

        ) {
            msg += "intent.type: ${intent1!!.type.toString()}\n"
            msg += "intent.data: ${intent1.data.toString()}\n"
            msg += "intent.dataString: ${intent1.dataString.toString()}\n"
            msg += "intent.extras: ${intent1.extras.toString()}\n"
            intent1?.extras?.keySet()?.forEach {
                msg + "bundle key: ${it}, value: ${intent1.extras?.get(it)}\n"
            }

            NfcAdapter.EXTRA_ID

            val extraId = intent1.getByteArrayExtra(NfcAdapter.EXTRA_ID)
            msg += "extraId: ${extraId}\n"
            val extraTag = intent1.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            msg += "extraTag: ${extraTag}\n"

        }
        findViewById<TextView>(R.id.textView1).text = msg
        Log.d("TAG", msg)
    }
}