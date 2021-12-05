package com.purenative.plumbus

import android.app.Application
import com.purenative.plumbus.core.base.appinitializers.AppInitializers
import com.purenative.plumbus.core.base.appinitializers.TimberInitializer
import com.purenative.plumbus.core.base.logging.TimberLogger

class PlumbusApplication: Application() {

    private val initializers by lazy {
        AppInitializers(
            setOf(
                TimberInitializer(TimberLogger())
            )
        )
    }

    override fun onCreate() {
        super.onCreate()
        initializers.init(this)
    }
}