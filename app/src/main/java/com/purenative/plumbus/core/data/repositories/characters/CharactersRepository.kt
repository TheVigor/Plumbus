package com.purenative.plumbus.core.data.repositories.characters

import com.purenative.plumbus.core.data.models.characters.CharacterResponse
import com.purenative.plumbus.core.data.models.characters.PageResponse
import com.purenative.plumbus.core.domain.models.characters.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getCharactersPage(page: Int): PageResponse<CharacterResponse>?

    suspend fun updateCharacter(id: Int)
    fun observerCharacter(id: Int): Flow<Character>

}