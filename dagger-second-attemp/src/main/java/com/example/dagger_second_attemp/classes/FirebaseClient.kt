package com.example.dagger_second_attemp.classes

import javax.inject.Inject

interface FirebaseClient {
    fun getKey(): String
}

class FirebaseClientImpl @Inject constructor(
    val okHttpClient: OkHttpClient,
) : FirebaseClient {
    override fun getKey(): String {
        return "FirebaseImpl Key :)"
    }
}