package com.dmsoft.infinite.di

import android.content.Context
import com.dmsoft.infinite.utils.SimplePreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private var context: Context) {

    @Provides
    @Singleton
    fun provideSharedPreferences(): SimplePreferences = SimplePreferences(context)
}