package com.eric.startupmatching

import android.app.Application
import kotlin.properties.Delegates


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MyApplication.appContext = this
    }
    companion object {
        var appContext: MyApplication by Delegates.notNull()
    }
}