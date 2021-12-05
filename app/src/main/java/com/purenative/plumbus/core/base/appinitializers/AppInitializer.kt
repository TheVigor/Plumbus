package com.purenative.plumbus.core.base.appinitializers

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}