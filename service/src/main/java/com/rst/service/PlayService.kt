package com.rst.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast

class PlayService : Service() {
    private var mPlayer: MediaPlayer? = null

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(
            this, "Служба создана",
            Toast.LENGTH_SHORT
        ).show();
        mPlayer = MediaPlayer.create(this, R.raw.music)
        mPlayer?.isLooping = false
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(
            this, "Служба запущена",
            Toast.LENGTH_SHORT
        ).show()
        mPlayer!!.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(
            this, "Служба остановлена",
            Toast.LENGTH_SHORT
        ).show()
        mPlayer!!.stop()
    }
}