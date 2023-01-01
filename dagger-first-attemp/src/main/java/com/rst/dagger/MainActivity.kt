package com.rst.dagger

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.rst.dagger.di.AnFA
import com.rst.dagger.di.AppComponent
import com.rst.dagger.di.DaggerAppComponent
import com.rst.dagger.second.AppComponent2
import javax.inject.Inject

const val TAG = "MYTAG"

class App : Application() {
    companion object {
        lateinit var cr: Application
    }
    lateinit var appComponent: AppComponent
    lateinit var appComponent2: AppComponent2

    override fun onCreate() {
        super.onCreate()
        cr = this
        appComponent = DaggerAppComponent.builder().context(this).build()
    }
}

val Context.appComponent: AppComponent
    get() = if (this is App) this.appComponent else this.applicationContext.appComponent

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var computer: Computer

    @Inject
    @AnFA
    lateinit var f: F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appComponent.inject(this)

        Log.d(TAG, computer.toString())

        Log.d(TAG, f.toString())
    }
}
