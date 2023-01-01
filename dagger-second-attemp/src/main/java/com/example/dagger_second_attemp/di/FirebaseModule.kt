package com.example.dagger_second_attemp.di

import com.example.dagger_second_attemp.classes.FirebaseClient
import com.example.dagger_second_attemp.classes.FirebaseClientImpl
import dagger.Binds
import dagger.Module

@Module
interface FirebaseModule {
    @Binds
    fun provideFirebaseClient(firebaseClient: FirebaseClientImpl): FirebaseClient
}