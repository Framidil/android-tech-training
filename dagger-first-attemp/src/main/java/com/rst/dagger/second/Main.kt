package com.rst.dagger.second


fun main() {
    val appComponent2: AppComponent2 = DaggerAppComponent2.create()
    val analytics = appComponent2.analytics
    analytics.track("a?")
}