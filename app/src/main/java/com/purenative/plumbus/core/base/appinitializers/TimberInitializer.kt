package com.purenative.plumbus.core.base.appinitializers

import android.app.Application
import com.purenative.plumbus.BuildConfig
import com.purenative.plumbus.core.base.logging.TimberLogger

class TimberInitializer(private val timberLogger: TimberLogger): AppInitializer {
    override fun init(application: Application) {
        timberLogger.setup(BuildConfig.DEBUG)
    }
}