package com.purenative.plumbus.core.di

import com.purenative.plumbus.features.characterdetails.CharacterDetailsViewModel
import com.purenative.plumbus.features.characters.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun viewModelModule() = module {
    viewModel { CharactersViewModel(get()) }
    viewModel { params -> CharacterDetailsViewModel(params.get(), get(), get()) }
}