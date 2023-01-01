package com.framidil.styleapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.framidil.styleapp.databinding.ActivityMainBinding
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.properties.Delegates.notNull

class MainActivity : AppCompatActivity() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var binding: ActivityMainBinding

    private var testReference: MainActivity by notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activityResultLauncher =
            registerForActivityResult(StartActivityForResult()) { result ->
                val a = result.data?.getIntExtra("resultInt", -1)
                binding.activityResultTextView.text = a.toString()
            }

        binding.goToSecondActivityButton.setOnClickListener {
            activityResultLauncher.launch(Intent(this, MainActivity2::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        val key = "key"

        val b = Bundle()
        val a = A(B(listOf(1,2 ,3)))
        b.putSerializable(key, a)

        println(b.getSerializable(key))
    }

    class A(val b: B): Serializable

    class B(val l: List<Int>) {
        override fun toString(): String {
            return l.toString()
        }
    }
}

fun main() {
    val time = "2022-12-30T20:10:59+03:00"
    val fullDateDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US)
    val result = OffsetDateTime.parse(time, fullDateDateFormat)
    println("$result ${result.toEpochSecond()}")

    val result3 = result.atZoneSameInstant(TimeZone.getDefault().toZoneId())
    println(result3)
    println()
    println("${result3.format(fullDateDateFormat)} ${result3.toEpochSecond()}")
}