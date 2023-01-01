package com.example.dagger_second_attemp.classes

import javax.inject.Inject


class FirstFragmentToy @Inject constructor(
    val okHttpClient: OkHttpClient,
) {
    val name = "Simple Toy :)"

    fun use() {}
}