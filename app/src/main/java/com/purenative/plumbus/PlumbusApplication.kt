package com.purenative.plumbus

import android.app.Application
import com.purenative.plumbus.core.base.appinitializers.AppInitializers
import com.purenative.plumbus.core.base.appinitializers.KoinInitializer
import org.koin.android.ext.android.inject

class PlumbusApplication : Application() {

    private val diInitializer by lazy { KoinInitializer() }
    private val initializers by inject<AppInitializers>()

    override fun onCreate() {
        super.onCreate()
        diInitializer.init(this)
        initializers.init(this)
    }
}