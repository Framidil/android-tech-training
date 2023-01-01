package com.rst.paging_3

import android.util.Log
import kotlinx.coroutines.delay
import kotlin.random.Random

object DB {
    val pageCount = 5
    val pageSize = 20

    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    suspend fun getItems(page: Int): List<String> {
        Log.d("TAG", "getItems START page: $page")
        delay(1500)
        return (1..pageSize)
            .map { Random.nextInt(3, 10) }
            .map { List(it) { charPool.random() }.joinToString("") }.also {
                Log.d("TAG", "getItems END page: $page")
            }
    }
}