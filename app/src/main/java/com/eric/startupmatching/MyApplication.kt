package com.eric.startupmatching

import android.app.Application
import kotlin.properties.Delegates


class MyApplication : Application() {
    // Depends on the flavor
//    val gameRepository: GameRepository
//        get() = ServiceLocator.provideTasksRepository(this)
    override fun onCreate() {
        super.onCreate()
//        appContext = applicationContext
        MyApplication.appContext = this
    }
    companion object {
//        var appContext: Context? = null
//            private set
        var appContext: MyApplication by Delegates.notNull()
    }
}