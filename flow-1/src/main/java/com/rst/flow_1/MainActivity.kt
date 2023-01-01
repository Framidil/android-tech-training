package com.rst.flow_1

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.rst.flow_1.databinding.ActivityMainBinding
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

const val tag = "FLOWTAG"

fun EventFlow() = MutableSharedFlow<String>()

class VM : ViewModel() {
    private val _toastChannel = Channel<String>()
    val toastFlow1 = _toastChannel.receiveAsFlow()

    private val _toastFlow2 = MutableStateFlow<String?>(null)
    val toastFlow2 = _toastFlow2

    private val _toastFlow3 = MutableSharedFlow<String?>(replay = 4)
    val toastFlow3 = _toastFlow3


    init {
        var a = 0
        viewModelScope.launch {
            while (true) {
                val new = a.toString()
                Log.d(tag, "send $new")
//                _toastFlow2.value = new
//                _toastChannel.send(new)
                _toastFlow3.emit(new)
                delay(3000)
                a++
            }
        }
    }
}

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val viewModel: VM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {


//                viewModel.toastFlow1.collect {
//                    Log.d(tag, "receive1 $it")
//                    binding.text.text = it
//                }
                }
//                launch {
//                    viewModel.toastFlow2.collect {
//                        Log.d(tag, "receive2 $it")
//                        binding.text.text = it
//                    }
//                }
                launch {
                    viewModel.toastFlow3.collect {
                        Log.d(tag, "receive3 $it")
                        binding.text.text = it
                    }
                }
            }
        }
    }
}