package com.example.dagger_second_attemp

import android.app.Application
import com.example.dagger_second_attemp.di.AppComponent
import com.example.dagger_second_attemp.di.DaggerAppComponent
import com.example.dagger_second_attemp.fragments.second.SecondComponent
import com.example.dagger_second_attemp.fragments.second.SecondComponentProvider

class MainApplication : Application(), SecondComponentProvider {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().luckyNumber(10).build()
    }

    override fun provideSecondComponent(): SecondComponent {
        return appComponent.secondComponent().create()
    }
}