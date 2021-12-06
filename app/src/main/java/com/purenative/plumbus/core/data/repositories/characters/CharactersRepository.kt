package com.purenative.plumbus.core.data.repositories.characters

import com.purenative.plumbus.core.domain.models.characters.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun observeCharacters(): Flow<List<Character>>
}