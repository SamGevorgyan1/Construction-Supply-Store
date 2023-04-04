package com.common

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

abstract class BaseCommonApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@BaseCommonApplication)
            androidLogger(Level.DEBUG)
            modules(getKoinModules())
        }
    }
    abstract fun getKoinModules(): List<Module>
}
