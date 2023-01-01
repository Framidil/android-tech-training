package com.framidil.databinding

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.framidil.databinding.databinding.ActivityViewModelBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyViewModel(val id: Int = 10) : ViewModel() {
    private var data: MutableLiveData<String>? = null

    fun getData(): MutableLiveData<String> {
        if (data == null) {
            data = MutableLiveData()
            loadData()
        }
        return data!!
    }

    fun loadData() {
        viewModelScope.launch {
            data!!.postValue("Start with id = $id")
            delay(2000)
            data!!.postValue("End")
        }
    }
}

class MyViewModelFactory(val id: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyViewModel(id) as T
    }
}

class ViewModelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewModelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_model)

//        val viewModel = ViewModelProvider(this, MyViewModelFactory(10))["KEY1", MyViewModel::class.java]
        val viewModel = ViewModelProvider(this/*, MyViewModelFactory(10)*/)[MyViewModel::class.java]
//        val viewModel by viewModels<MyViewModel>()

        viewModel.getData().observe(this) {
            binding.viewModelTextView.text = it
            Log.d("TAG", "SET $it")
        }
        binding.viewModelButton.setOnClickListener {
            viewModel.loadData()
        }
    }
}

class ViewModelFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel by activityViewModels<MyViewModel>()
    }
}