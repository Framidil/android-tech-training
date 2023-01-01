package com.example.dagger_second_attemp.di

import com.example.dagger_second_attemp.fragments.second.SecondComponent
import com.example.dagger_second_attemp.fragments.second.SecondFragment
import dagger.Subcomponent

@Subcomponent
interface SecondComponentImpl : SecondComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): SecondComponentImpl
    }

    override fun inject(fragment: SecondFragment)
}