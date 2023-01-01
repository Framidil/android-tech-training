package com.rst.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class MaterialRangeDatePickerFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val start: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        start.set(2020, 1, 1)
        val end: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        end.set(2021, 1, 1)
        return MaterialDatePicker.Builder.dateRangePicker()
            .setCalendarConstraints(
                CalendarConstraints.Builder()
                    .setStart(start.timeInMillis)
                    .setEnd(end.timeInMillis).build()
            )
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR).setTheme(R.style.a)
            .build().dialog!!
    }
}