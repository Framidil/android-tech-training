package com.example.dagger_hilt_attemp.classes

import javax.inject.Inject

class OkHttpClientImpl @Inject constructor() : OkHttpClient {
    override val version: String
        get() = "1.2.1"
}