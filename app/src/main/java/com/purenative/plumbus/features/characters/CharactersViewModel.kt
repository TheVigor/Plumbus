package com.purenative.plumbus.features.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purenative.plumbus.core.data.characters.CharactersRepository
import kotlinx.coroutines.launch

class CharactersViewModel(private val charactersRepository: CharactersRepository): ViewModel() {
    fun getCharacters() {
        viewModelScope.launch {
            charactersRepository.getCharacters()
        }
    }
}