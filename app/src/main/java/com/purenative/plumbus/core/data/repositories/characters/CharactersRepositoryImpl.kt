package com.purenative.plumbus.core.data.repositories.characters

import com.purenative.plumbus.core.data.PlumbusApi
import com.purenative.plumbus.core.data.daos.CharacterDao
import com.purenative.plumbus.core.data.mappers.characters.CharacterEntityToCharacterMapper
import com.purenative.plumbus.core.data.mappers.characters.CharacterToCharacterEntityMapper
import com.purenative.plumbus.core.data.mappers.characters.CharacterResponseToCharacterMapper
import com.purenative.plumbus.core.data.models.characters.CharacterResponse
import com.purenative.plumbus.core.data.models.characters.PageResponse
import com.purenative.plumbus.core.domain.models.characters.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CharactersRepositoryImpl(
    private val plumbusApi: PlumbusApi,
    private val characterDao: CharacterDao,
    val responseMapper: CharacterResponseToCharacterMapper,
    val entityMapper: CharacterToCharacterEntityMapper,
    val domainMapper: CharacterEntityToCharacterMapper
): CharactersRepository {

    override suspend fun getCharactersPage(page: Int): PageResponse<CharacterResponse>? {
        return plumbusApi.getCharacters(page = page).body()
    }

    override suspend fun updateCharacter(id: Int) {
        val response = plumbusApi.getCharacter(id)
        if (response.isSuccessful) {
            response.body()?.let {
                characterDao.insert(it.toCharacterEntity())
            }
        }
    }

    override fun observerCharacter(id: Int): Flow<Character> {
        return characterDao.getCharacter(id).map { domainMapper.map(it) }
    }


}