package com.rst.flow

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

const val tag = "myTag"

class MainViewModel : ViewModel() {
    private val _myLiveData = MutableLiveData(1)
    val myLiveData: LiveData<Int> = _myLiveData

    val myLiveData2 = liveData<Int> {
        emit(1)
        while (true) {
            emit(this.latestValue!! + 1)
            delay(125)
            if (this.latestValue == 100) {
                emitSource(myLiveData)
                break
            }
        }
    }

//    val myFlow = flow<Int> {
//        var a = 1
//        emit(a)
//        while (true) {
//            emit(a++)
//            delay(125)
//        }
//    }

    val myFlow = (1..1000).asFlow().map {
        delay(100)
        it
    }

    val myStateFlow = MutableStateFlow(1)
    val mySharedFlow = MutableSharedFlow<Int>()

    class A

    val myRepeatFlow = MutableStateFlow(A())

    init {
        viewModelScope.launch {
            var a = 1
            while (true) {
                _myLiveData.value = a++
                delay(1)
            }
        }

        viewModelScope.launch {
            var a = 1
            while (true) {
                val newValue = a++
                Log.d(tag, "myStateFlow emit $newValue. Scope: ${Thread.currentThread().name}")
                myStateFlow.emit(newValue)
                delay(100)
            }
        }

        viewModelScope.launch {
            var a = 1
            while (true) {
                val newValue = a++
                Log.d(tag, "mySharedFlow emit $newValue. Scope: ${Thread.currentThread().name}")
//                Log.d(tag, "mySharedFlow START")
                mySharedFlow.emit(newValue)
//                Log.d(tag, "mySharedFlow END")
                delay(100)
            }
        }

        viewModelScope.launch {
            val a = A()
            while (true) {
                myRepeatFlow.emit(a)
                delay(100)
            }
        }
    }
}

class MainActivity : AppCompatActivity() {
    lateinit var tv1: TextView
    lateinit var tv2: TextView
    lateinit var tv3: TextView
    lateinit var tv4: TextView
    lateinit var tv5: TextView

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(tag, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv1 = findViewById(R.id.tv1)
        tv2 = findViewById(R.id.tv2)
        tv3 = findViewById(R.id.tv3)
        tv4 = findViewById(R.id.tv4)
        tv5 = findViewById(R.id.tv5)


        setObservers()
    }

    private fun setObservers() {
        viewModel.myLiveData
            .observe(this) {
                Log.d(tag, "myLiveData set $it")

                tv1.text = it.toString()
                var a = 0
                repeat(1e7.toInt()) { a++ }
            }

        viewModel.myLiveData2.observe(this) {
            Log.d(tag, "myLiveData2 set $it")
            tv2.text = it.toString()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.myFlow
                        .buffer()
//                        .distinctUntilChanged()
                        .collect {
                            Log.d(tag, "myFlow set $it")
                            tv3.text = it.toString()
                        }
                }

                launch {
                    viewModel.myStateFlow
                        .collect {
                            Log.d(
                                tag,
                                "myStateFlow set $it. Scope: ${Thread.currentThread().name}"
                            )
                            repeat(1) { a ->
                                tv4.text = it.toString()
                            }
                            delay(500)
                        }
                }

                launch {
                    viewModel.mySharedFlow
                        .collect {
                            Log.d(
                                tag,
                                "mySharedFlow set $it. Scope: ${Thread.currentThread().name}"
                            )
                            tv5.text = it.toString()
                            delay(3000)
                        }
                }

                launch {
                    viewModel.myRepeatFlow.collect {
                        Log.d(tag, "myRepeatFlow set $it")
                    }
                }
            }
        }
    }

    override fun onStart() {
        Log.d(tag, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(tag, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(tag, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(tag, "onStop")
        super.onStop()
    }
}