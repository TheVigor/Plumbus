package com.purenative.plumbus.core.di

import com.purenative.plumbus.core.data.mappers.characters.CharacterResponseToCharacterMapper
import org.koin.dsl.module

fun mappersModule() = module {
    single {
        CharacterResponseToCharacterMapper()
    }
}