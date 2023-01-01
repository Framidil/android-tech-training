package com.framidil.databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.framidil.databinding.dataBindingModel.Employee
import com.framidil.databinding.databinding.ActivityDataBindingBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DataBindingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataBindingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding)

        val employers = listOf(
            Employee(1, "First", "Moskow"),
            Employee(2, "Second", "NewYork"),
            Employee(3, "Third", "Budapesht")
        )

        var index = 0
        lifecycleScope.launch {
            while (true) {
                binding.employee = employers[index++ % employers.size]
                delay(1_000)
            }
        }
    }
}