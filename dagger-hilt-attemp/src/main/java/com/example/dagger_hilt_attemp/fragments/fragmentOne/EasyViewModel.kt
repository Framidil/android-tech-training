package com.example.dagger_hilt_attemp.fragments.fragmentOne

import androidx.lifecycle.ViewModel
import com.example.dagger_hilt_attemp.classes.ViewModelBox
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EasyViewModel @Inject constructor(
    val box: ViewModelBox
) : ViewModel() {
    fun ping() {
        box.name
    }
}