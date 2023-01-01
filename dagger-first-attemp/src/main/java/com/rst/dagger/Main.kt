package com.rst.dagger

import com.rst.dagger.di.AppComponent
import com.rst.dagger.di.DaggerAppComponent

fun main() {
    val appComponent: AppComponent = DaggerAppComponent.builder().build()
    val computer = appComponent.computer()
    print(computer)
}