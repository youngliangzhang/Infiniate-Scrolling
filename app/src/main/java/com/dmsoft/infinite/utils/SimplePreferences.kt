package com.dmsoft.infinite.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.annotation.NonNull
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class SimplePreferences @Inject constructor(context: Context) {

    var imageLoadCount = 0
    companion object {
        const val KEY_LOAD_IMAGE_COUNT = "load_image_count"
    }

    private var preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    init {
        if (!contains(KEY_LOAD_IMAGE_COUNT)) {
            putValue(KEY_LOAD_IMAGE_COUNT, 0)
        }else {
            imageLoadCount = getValue(KEY_LOAD_IMAGE_COUNT, 0)
        }
    }

    private fun putValue(key: String, value: Any): SimplePreferences {
        val e = preferences.edit()
        try {
            when (value) {
                is String -> e.putString(key, value)
                is Boolean -> e.putBoolean(key, value)
                is Int -> e.putInt(key, value)
                is Long -> e.putLong(key, value)
                is Float -> e.putFloat(key, value)
                is Set<*> -> e.putStringSet(key, value as Set<String>)
                else -> throw ClassCastException(value.javaClass.name + " is not allowed type of object.")
            }
        } finally {
            e.apply()
        }

        return this
    }

    fun <T> getValue(key: String, @NonNull defaultValue: T): T {
        return when (defaultValue) {
            is String -> preferences.getString(key, defaultValue) as T
            is Boolean -> java.lang.Boolean.valueOf(preferences.getBoolean(key, defaultValue)) as T
            is Int -> Integer.valueOf(preferences.getInt(key, defaultValue)) as T
            is Long -> java.lang.Long.valueOf(preferences.getLong(key, defaultValue)) as T
            is Float -> java.lang.Float.valueOf(preferences.getFloat(key, defaultValue)) as T
            is Set<*> -> preferences.getStringSet(key, defaultValue as Set<String>) as T
            else -> throw ClassCastException(defaultValue.toString() + " is not allowed type of object.")
        }
    }

    fun increaseLoadingCount() {
        imageLoadCount++
        putValue(KEY_LOAD_IMAGE_COUNT, imageLoadCount)
    }

    operator fun contains(key: String): Boolean = preferences.contains(key)
}