package com.dmsoft.infinite.di

import com.dmsoft.infinite.activities.MainActivity
import com.dmsoft.infinite.activities.SplashActivity
import com.dmsoft.infinite.adapter.ImageAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ImageDownloadModule::class])
interface AppComponent {

    // activity
    fun inject(target: SplashActivity)
    fun inject(target: MainActivity)

    // adapter
    fun inject(target: ImageAdapter)

}