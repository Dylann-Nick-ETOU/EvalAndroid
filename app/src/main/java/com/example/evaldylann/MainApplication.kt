package com.example.evaldylann

import android.app.Application
import com.example.evaldylann.di.databaseModule
import com.example.evaldylann.di.moviesModule
import com.example.evaldylann.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(networkModule, databaseModule, moviesModule)
        }
    }
}
