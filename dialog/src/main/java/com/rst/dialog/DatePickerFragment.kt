package com.rst.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DatePickerFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_date, null)
        return AlertDialog.Builder(activity)
            .setView(v)
            .setTitle("MyTitle")
            .setPositiveButton("positive", null)
            .create().also {
                lifecycleScope.launch {
                    repeat(3) {
                        delay(1000)
                        parentFragmentManager.setFragmentResult("MA KEY", bundleOf(Pair("a", "b")))
                    }
                }
            }
    }
}