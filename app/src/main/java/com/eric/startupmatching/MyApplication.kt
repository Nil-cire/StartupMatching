package com.eric.startupmatching

import android.app.Application
import android.content.Context


class MyApplication : Application() {
    // Depends on the flavor
//    val gameRepository: GameRepository
//        get() = ServiceLocator.provideTasksRepository(this)
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
    companion object {
        var appContext: Context? = null
            private set
    }
}