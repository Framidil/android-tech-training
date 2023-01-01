package com.framidil.databinding

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.framidil.databinding.databinding.ActivityMainBinding


object DataController {
    val stringList = MutableLiveData(listOf("a"))
    val name = MutableLiveData("a")

    val upperStringList: LiveData<List<String>> = Transformations.map(stringList) {
        it.map(String::uppercase)
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        DataController.upperStringList.observe(this) {
            binding.myTextView.text = it.joinToString(separator = ", ")
        }

        DataController.name.observe(this) {
            binding.mySecondTextView.text = it
        }

        object : CountDownTimer(5_000, 1_000) {
            override fun onTick(millisUntilFinished: Long) {
                DataController.stringList.postValue(DataController.stringList.value?.plus("a"))
                DataController.name.postValue(DataController.name.value + "a")
            }

            override fun onFinish() {
                binding.myTextView.text = "END"
                binding.mySecondTextView.text = "END"
            }

        }.start()
    }
}