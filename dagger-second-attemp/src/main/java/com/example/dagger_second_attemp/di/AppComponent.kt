package com.example.dagger_second_attemp.di

import com.example.dagger_second_attemp.MainActivity
import com.example.dagger_second_attemp.fragments.first.FirstFragment
import com.example.dagger_second_attemp.fragments.second.SecondFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [FirebaseModule::class, SubcomponentsModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun luckyNumber(number: Int): Builder
        fun build(): AppComponent
    }

    fun secondComponent(): SecondComponentImpl.Factory

    fun inject(activity: MainActivity)
    fun inject(fragment: FirstFragment)
    fun inject(fragment: SecondFragment)
}