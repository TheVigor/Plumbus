package com.purenative.plumbus.features.characterdetails

import com.purenative.plumbus.core.domain.models.characters.Character

data class CharacterDetailsViewState(
    val character: Character,
    val isFollowed: Boolean,
    val refreshing: Boolean
) {
    companion object {
        val EMPTY = CharacterDetailsViewState(
            character = Character.EMPTY,
            isFollowed = false,
            refreshing = false
        )
    }
}