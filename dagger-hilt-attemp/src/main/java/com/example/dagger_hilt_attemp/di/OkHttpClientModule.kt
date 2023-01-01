package com.example.dagger_hilt_attemp.di

import com.example.dagger_hilt_attemp.classes.OkHttpClient
import com.example.dagger_hilt_attemp.classes.OkHttpClientImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
interface OkHttpClientModule {
    @Binds
    fun bindOkhttpClient(okHttpClient: OkHttpClientImpl): OkHttpClient
}