package com.purenative.plumbus.core.data.repositories.characters

import androidx.paging.PagingSource
import com.purenative.plumbus.core.data.PlumbusApi
import com.purenative.plumbus.core.data.daos.CharacterDao
import com.purenative.plumbus.core.data.daos.FollowingCharacterDao
import com.purenative.plumbus.core.data.entities.CharacterEntity
import com.purenative.plumbus.core.data.entities.FollowingCharacterEntity
import com.purenative.plumbus.core.data.models.characters.CharacterResponse
import com.purenative.plumbus.core.data.models.characters.PageResponse
import com.purenative.plumbus.core.domain.models.characters.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharactersRepositoryImpl(
    private val plumbusApi: PlumbusApi,
    private val characterDao: CharacterDao,
    private val followingCharacterDao: FollowingCharacterDao
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
        return characterDao.observeCharacter(id).map { it.toCharacter() }
    }

    override suspend fun getCharacter(id: Int): CharacterEntity? {
        return characterDao.getCharacter(id)
    }

    override fun getPagedFollowingCharacters(filter: String?): PagingSource<Int, FollowingCharacterEntity> {
        val filtered = filter != null && filter.trim().isNotEmpty()

        return if (filtered) {
            followingCharacterDao.getFollowingCharactersFiltered(filter)
        } else {
            followingCharacterDao.getFollowingCharacters()
        }
    }

    override suspend fun addFollowingCharacter(character: FollowingCharacterEntity) {
        return followingCharacterDao.insert(character)
    }

    override suspend fun deleteFollowingCharacter(characterId: Int) {
        followingCharacterDao.delete(characterId)
    }

    override suspend fun getFollowingCharacter(id: Int): FollowingCharacterEntity? {
        return followingCharacterDao.getFollowingCharacter(id)
    }

    override suspend fun isCharacterFollowing(characterId: Int): Boolean {
        return followingCharacterDao.entryCountWithCharacterId(characterId) > 0
    }

    override fun observeIsCharacterFollowing(characterId: Int): Flow<Boolean> {
        return followingCharacterDao.observeEntryCountWithCharacterId(characterId)
            .map { it > 0 }
    }


}