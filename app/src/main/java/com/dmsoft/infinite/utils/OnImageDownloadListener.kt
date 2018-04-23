package com.dmsoft.infinite.utils

import android.graphics.Bitmap

interface OnImageDownloadListener {
    fun onComplete(position: Int, bitmap: Bitmap?)
}