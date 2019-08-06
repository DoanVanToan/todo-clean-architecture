package com.toandoan.cleanarchitechture

import android.app.Application
import com.toandoan.cleanarchitechture.di.appModule
import com.toandoan.cleanarchitechture.di.dataModule
import com.toandoan.cleanarchitechture.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication() : Application() {
    private val modules = listOf(mainModule, appModule, dataModule)

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(modules)
        }
    }
}