package com.dmsoft.infinite

import android.app.Application
import com.dmsoft.infinite.di.AppComponent
import com.dmsoft.infinite.di.AppModule
import com.dmsoft.infinite.di.DaggerAppComponent
import com.dmsoft.infinite.di.ImageDownloadModule

class App: Application() {

    lateinit var appComponent: AppComponent

    companion object {
        lateinit var instance: App
    }

    init {
        instance = this
        initDaggerComponent()
    }

    private fun initDaggerComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .imageDownloadModule(ImageDownloadModule())
                .build()
    }

}