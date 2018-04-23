package com.dmsoft.infinite.utils

import android.util.Log
import java.util.concurrent.*


class ImageDownloadThreadPoolManager {

    companion object {
        private const val DEFAULT_THREAD_POOL_SIZE = 4
    }

    private var mExecutorService: ExecutorService
    private var mRunningTaskList: MutableMap<Int, Future<Any?>> = HashMap()


    init {
        mExecutorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE, BackgroundThreadFactory())
    }


    fun addCallable(position: Int, callable: ImageCallable) {
        mRunningTaskList[position] = mExecutorService.submit(callable)
    }

    fun cancelTask(position: Int) {
        synchronized(this) {
            mRunningTaskList[position]?.let {
                if (!it.isDone)
                    it.cancel(true)
            }
            mRunningTaskList.remove(position)
        }
    }

    private class BackgroundThreadFactory : ThreadFactory {

        override fun newThread(runnable: Runnable): Thread = Thread(runnable).apply {
            name = "CustomThread$sTag"
            priority = android.os.Process.THREAD_PRIORITY_BACKGROUND
            uncaughtExceptionHandler = Thread.UncaughtExceptionHandler { thread, ex -> Log.e("Thread", thread.name + " encountered an error: " + ex.message) }
        }

        companion object {
            private const val sTag = 1
        }
    }
}