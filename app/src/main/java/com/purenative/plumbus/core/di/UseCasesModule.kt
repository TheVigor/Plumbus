package com.purenative.plumbus.core.di

import com.purenative.plumbus.core.domain.usecases.ChangeCharacterFollowStatus
import com.purenative.plumbus.core.domain.usecases.UpdateCharacterDetails
import org.koin.dsl.module

fun usecasesModule() = module {
    single { UpdateCharacterDetails(get()) }
    single { ChangeCharacterFollowStatus(get()) }
}