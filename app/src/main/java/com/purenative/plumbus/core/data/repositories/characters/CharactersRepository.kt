package com.purenative.plumbus.core.data.repositories.characters

import com.purenative.plumbus.core.data.models.characters.CharacterResponse
import com.purenative.plumbus.core.data.models.characters.PageResponse
import com.purenative.plumbus.core.domain.models.characters.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun observeCharacters(): Flow<List<Character>>
    suspend fun getCharactersPage(page: Int): PageResponse<CharacterResponse>?
}