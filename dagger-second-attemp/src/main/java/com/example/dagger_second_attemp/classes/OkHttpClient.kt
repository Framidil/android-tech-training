package com.example.dagger_second_attemp.classes

import javax.inject.Inject

class OkHttpClient @Inject constructor(
    val number: Int,
) {

    val version = "1.0.1"
    fun connect() {
        // show Toast
    }
}