package com.dmsoft.infinite.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.util.concurrent.Callable

class ImageCallable(private val position: Int, private val url: String, private val onImageDownloadListener: OnImageDownloadListener) : Callable<Any?> {

    @Throws(Exception::class)
    override fun call(): Any? {
        try {
            if (Thread.interrupted()) throw InterruptedException()

            var bitmap: Bitmap? = null
            try {
                bitmap = BitmapFactory.decodeStream(java.net.URL(url).openStream())
            } catch (e: Exception) {
                Log.e("Error Message", e.message)
                e.printStackTrace()
            }

            onImageDownloadListener.onComplete(position, bitmap)
            return null

        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return null
    }
}