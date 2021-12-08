package com.purenative.plumbus.core.di

import com.purenative.plumbus.core.domain.observers.ObserveCharacterDetails
import com.purenative.plumbus.core.domain.observers.ObserveCharacters
import org.koin.dsl.module

fun observersModule() = module {
    single { ObserveCharacters(get(), get()) }
    single { ObserveCharacterDetails(get()) }

}