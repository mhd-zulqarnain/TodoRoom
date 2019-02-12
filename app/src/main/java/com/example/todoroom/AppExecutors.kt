package com.example.todoroom

import android.os.Handler
import android.os.Looper
import com.example.todoroom.AppDatabase.Companion.LOCK
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class AppExecutors private constructor(var diskIO: Executor,var networkIO: Executor, var mainThread: Executor) {



    companion object {

        var sInstance: AppExecutors? = null
        fun getInstance(): AppExecutors {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = AppExecutors(
                        Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        MainThreadExecutor()
                    )
                }
            }
            return sInstance!!
        }

    }


    fun diskIO(): Executor {
        return diskIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    fun networkIO(): Executor {
        return networkIO
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}