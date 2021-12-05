package com.purenative.plumbus.core.base.appinitializers

import android.app.Application

class AppInitializers(private val initializers: Set<AppInitializer>) {
    fun init(application: Application) {
        initializers.forEach {
            it.init(application)
        }
    }
}