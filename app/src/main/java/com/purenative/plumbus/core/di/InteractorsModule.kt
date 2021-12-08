package com.purenative.plumbus.core.di

import com.purenative.plumbus.core.domain.interactors.UpdateCharacterDetails
import org.koin.dsl.module

fun interactorsModule() = module {
    single { UpdateCharacterDetails(get()) }
}