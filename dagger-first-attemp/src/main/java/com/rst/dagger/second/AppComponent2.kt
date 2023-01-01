package com.rst.dagger.second

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoSet
import javax.inject.Inject

@Component(modules = [AppModule2::class])
interface AppComponent2 {
    val analytics: Analytics
}

@Module
abstract class AppModule2 {
    @[Binds IntoSet]
    abstract fun bindFacebookTracker(tracker: FacebookTracker): Tracker

    @[Binds IntoSet]
    abstract fun bindFirebaseTracker(tracker: FirebaseTracker): Tracker
}

class Analytics @Inject constructor(
    private val trackers: Set<@JvmSuppressWildcards Tracker>
) {
    fun track(event: String) {
        trackers.forEach {
            it.track(event)
        }
    }
}

interface Tracker {
    fun track(event: String)
}

class FacebookTracker @Inject constructor() : Tracker {
    override fun track(event: String) {
        println("FacebookTracker: $event")
    }
}

class FirebaseTracker @Inject constructor() : Tracker {
    override fun track(event: String) {
        println("FirebaseTracker: $event")
    }
}
