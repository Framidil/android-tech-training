package com.rst.dagger.di

import android.content.Context
import com.rst.dagger.*
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Component(modules = [AppModule::class, AppBindModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }

    fun inject(mainActivity: MainActivity)

    fun computer(): Computer
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AnFA

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AnFB


@Module
class AppModule {
    @Provides
    @AnFA
    fun provideFA(context: Context): F {
        return FA(context)
    }

    @Provides
    @AnFB
    fun provideFB(): F {
        return FB()
    }

    @Provides
    fun provideComputer(
        processor: Processor,
        motherboard: Motherboard,
        ram: RAM
    ): Computer {
        return Computer(processor, motherboard, ram)
    }

    @Provides
    fun provideProcessor(): Processor {
        return Processor()
    }

    @Provides
    fun provideMotherboard(): Motherboard {
        return Motherboard()
    }

    @Provides
    fun provideRAM(): RAM {
        return RAM()
    }
}

@Module
interface AppBindModule {
//    @Binds
//    fun provideF(fa: FA): F
}