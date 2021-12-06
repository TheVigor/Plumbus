package com.purenative.plumbus.features.characters

import com.purenative.plumbus.core.domain.models.characters.Character

data class CharactersViewState(
    val characters: List<Character> = emptyList(),
    val refreshing: Boolean = false
) {
    companion object {
        val EMPTY = CharactersViewState()
    }
}