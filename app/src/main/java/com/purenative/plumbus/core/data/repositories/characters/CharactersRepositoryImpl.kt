package com.purenative.plumbus.core.data.repositories.characters

import com.purenative.plumbus.core.data.PlumbusApi
import com.purenative.plumbus.core.data.mappers.characters.CharacterResponseToCharacterMapper
import com.purenative.plumbus.core.data.mappers.forLists
import com.purenative.plumbus.core.data.models.characters.CharacterResponse
import com.purenative.plumbus.core.data.models.characters.PageResponse
import com.purenative.plumbus.core.domain.models.characters.Character
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class CharactersRepositoryImpl(
    private val plumbusApi: PlumbusApi,
    private val responseMapper: CharacterResponseToCharacterMapper): CharactersRepository {
    override fun observeCharacters(): Flow<List<Character>> {
        return flow {
            emit(plumbusApi.getCharacters().results.map { responseMapper.map(it) })
        }
    }
}