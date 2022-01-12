package com.purenative.plumbus.core.base.appinitializers

import android.app.Application
import com.purenative.plumbus.BuildConfig
import com.purenative.plumbus.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class KoinInitializer: AppInitializer {
    override fun init(application: Application) {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(application)
            modules(
                listOf(
                    appInitializersModule(),
                    dataModule(),
                    viewModelModule(),
                    observersModule(),
                    usecasesModule()
                )
            )
        }
    }
}