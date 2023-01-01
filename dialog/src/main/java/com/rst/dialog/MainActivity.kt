package com.rst.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rst.dialog.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.button1.setOnClickListener {
            val dialog = DatePickerFragment()
            dialog.show(supportFragmentManager, "DialogDate")
        }

        var timePicker = false
        binding.button2.setOnClickListener {
            if (timePicker) {
                TimePickerDialog(
                    this,
                    { view, hourOfDay, minute -> }, 1, 30, true
                ).show()
            } else {
                DatePickerDialog(this).show()
            }
            timePicker = !timePicker
        }

        var rangePicker = true
        binding.button3.setOnClickListener {
            if (rangePicker) {
                val start: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                start.set(2020, 1, 1)
                val end: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                end.set(2021, 1, 1)
                MaterialDatePicker.Builder.dateRangePicker()
                    .setCalendarConstraints(
                        CalendarConstraints.Builder()
                            .setStart(start.timeInMillis)
                            .setEnd(end.timeInMillis).build()
                    )
                    .setInputMode(INPUT_MODE_CALENDAR)
                    .setTheme(R.style.a)
                    .build()
                MaterialRangeDatePickerFragment()
            } else {
                MaterialDatePicker.Builder.datePicker().setTheme(R.style.a).build()
            }
                .show(supportFragmentManager, "DateRangePicker3")

//            rangePicker = !rangePicker
        }

        binding.button4.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("title")
                .setSingleChoiceItems(arrayOf("a", "b", "c").let { it + it + it }, 0) { _, _ ->

                }
                .setPositiveButton("positive", null)
                .setNegativeButton("negative", null)
                .show()
        }

        supportFragmentManager.setFragmentResultListener("MA KEY", this) { key, b ->
            Log.d("TAG", "key: $key, bundle: ${b.get("a")}")
        }

        binding.button5.setOnClickListener {
            FullScreenDialogFragment().show(supportFragmentManager, "")
        }

        binding.button6.setOnClickListener {
            FullScreenDialogWithLoader().show(supportFragmentManager, "")
        }
    }
}