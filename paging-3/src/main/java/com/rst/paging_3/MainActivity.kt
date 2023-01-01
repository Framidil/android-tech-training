package com.rst.paging_3

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.paging.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val myPagingFlow = Pager(
        PagingConfig(
            pageSize = DB.pageSize,
            enablePlaceholders = true,
            jumpThreshold = 20
        )
    ) {
        MyPagingSource()
    }.flow. map { pagingData ->
        pagingData.insertSeparators { s: String?, s2: String? ->
            if (s != s2) {
                "---------"
            } else {
                null
            }
        }.insertSeparators { s: String?, s2: String? ->
            if (s == "---------") {
                "~~~~~~"
            } else null
        }
    } .cachedIn(viewModelScope)
}

class MainActivity : AppCompatActivity() {
    lateinit var rv: RecyclerView

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv = findViewById(R.id.rv)

        rv.layoutManager = LinearLayoutManager(this)
        val adapter = Adapter()
        rv.adapter = adapter

        lifecycleScope.launch {
            launch {

                lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.myPagingFlow.collectLatest {
                        adapter.submitData(it)
                    }
                }
            }

            launch {
                delay(1000)
                rv.scrollToPosition(50)
            }
        }
    }
}