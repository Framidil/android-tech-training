package com.example.dagger_hilt_attemp.fragments.fragmentOne

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dagger_hilt_attemp.classes.ViewModelBox
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class HardViewModel @AssistedInject constructor(
    viewModelBox: ViewModelBox,
    @Assisted("first_id") id: Int,
) : ViewModel() {
    companion object {
        fun provideFactory(factory: Factory, id: Int) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(id) as T
            }
        }
    }

    val suffix = "with ViewModel + ${viewModelBox.name} + id: $id"

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("first_id") id1: Int
        ): HardViewModel
    }
}