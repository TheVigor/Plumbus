package com.purenative.plumbus.core.di

import com.purenative.plumbus.core.base.appinitializers.AppInitializers
import com.purenative.plumbus.core.base.appinitializers.TimberInitializer
import com.purenative.plumbus.core.base.logging.TimberLogger
import org.koin.dsl.module

fun appInitializersModule() = module {

    single {
        TimberLogger()
    }

    single {
        TimberInitializer(get())
    }

    single {
        AppInitializers(
            setOf(
                get() as TimberInitializer
            )
        )
    }
}