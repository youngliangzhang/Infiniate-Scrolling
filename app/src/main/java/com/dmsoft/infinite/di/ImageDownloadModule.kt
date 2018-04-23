package com.dmsoft.infinite.di

import com.dmsoft.infinite.utils.ImageDownloadThreadPoolManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImageDownloadModule {

    @Provides
    @Singleton
    fun provideImageDownloadModule(): ImageDownloadThreadPoolManager = ImageDownloadThreadPoolManager()
}