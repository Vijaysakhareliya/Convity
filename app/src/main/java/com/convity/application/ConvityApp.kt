package com.convity.application

import android.app.Application
import com.convity.di.networkModule
import com.convity.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ConvityApp : Application() {
    companion object {
        private lateinit var instance: ConvityApp

        fun getInstance(): ConvityApp = instance


    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initApp()
    }

    private fun initApp() {
        startKoin {
            androidContext(this@ConvityApp)
            modules(
                listOf(
                    networkModule,
                    viewModelModule
                )
            )
        }
    }
}