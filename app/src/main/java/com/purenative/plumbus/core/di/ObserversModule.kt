package com.purenative.plumbus.core.di

import com.purenative.plumbus.core.domain.observers.ObserveCharacterDetails
import com.purenative.plumbus.core.domain.observers.ObserveCharacterFollowStatus
import com.purenative.plumbus.core.domain.observers.ObserveCharacters
import com.purenative.plumbus.core.domain.observers.ObserveFollowingCharacters
import org.koin.dsl.module

fun observersModule() = module {
    single { ObserveCharacters(get(), get()) }
    single { ObserveCharacterDetails(get()) }
    single { ObserveFollowingCharacters(get()) }
    single { ObserveCharacterFollowStatus(get()) }

}