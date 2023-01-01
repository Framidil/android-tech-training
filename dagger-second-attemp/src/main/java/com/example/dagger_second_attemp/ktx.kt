package com.example.dagger_second_attemp

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.dagger_second_attemp.di.AppComponent

val Fragment.appComponent
    get() = requireContext().appComponent

val Context.appComponent: AppComponent
    get() = if (this is MainApplication) {
        this.appComponent
    } else {
        this.applicationContext.appComponent
    }