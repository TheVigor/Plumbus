package com.purenative.plumbus.core.data.repositories.characters

import com.purenative.plumbus.core.data.PlumbusApi
import com.purenative.plumbus.core.data.mappers.characters.CharacterEntityToCharacterMapper
import com.purenative.plumbus.core.data.mappers.characters.CharacterToCharacterEntityMapper
import com.purenative.plumbus.core.data.mappers.characters.CharacterResponseToCharacterMapper
import com.purenative.plumbus.core.data.models.characters.CharacterResponse
import com.purenative.plumbus.core.data.models.characters.PageResponse
import com.purenative.plumbus.core.domain.models.characters.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharactersRepositoryImpl(
    private val plumbusApi: PlumbusApi,
    val responseMapper: CharacterResponseToCharacterMapper,
    val entityMapper: CharacterToCharacterEntityMapper,
    val domainMapper: CharacterEntityToCharacterMapper
): CharactersRepository {
    override fun observeCharacters(): Flow<List<Character>> {
        return flow {
            emit(plumbusApi.getCharacters(page = 1).body()?.results?.map { responseMapper.map(it) }
                ?: emptyList())
        }
    }

    override suspend fun getCharactersPage(page: Int): PageResponse<CharacterResponse>? {
        return plumbusApi.getCharacters(page = page).body()
    }


}