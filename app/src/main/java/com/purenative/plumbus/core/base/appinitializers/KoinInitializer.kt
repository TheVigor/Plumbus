package com.purenative.plumbus.core.base.appinitializers

import android.app.Application
import com.purenative.plumbus.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KoinInitializer: AppInitializer {
    override fun init(application: Application) {
        startKoin {
            androidLogger()
            androidContext(application)
            modules(
                listOf(
                    appInitializersModule(),
                    dataModule(),
                    viewModelModule(),
                    mappersModule(),
                    observersModule()
                )
            )
        }
    }
}