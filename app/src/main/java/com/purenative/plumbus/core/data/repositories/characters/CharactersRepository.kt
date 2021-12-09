package com.purenative.plumbus.core.data.repositories.characters

import androidx.paging.PagingSource
import com.purenative.plumbus.core.data.entities.CharacterEntity
import com.purenative.plumbus.core.data.entities.FollowingCharacterEntity
import com.purenative.plumbus.core.data.models.characters.CharacterResponse
import com.purenative.plumbus.core.data.models.characters.PageResponse
import com.purenative.plumbus.core.domain.models.characters.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getCharactersPage(page: Int): PageResponse<CharacterResponse>?

    suspend fun updateCharacter(id: Int)
    fun observerCharacter(id: Int): Flow<Character>
    suspend fun getCharacter(id: Int): CharacterEntity?

    fun getPagedFollowingCharacters(): PagingSource<Int, FollowingCharacterEntity>

    suspend fun addFollowingCharacter(character: FollowingCharacterEntity)
    suspend fun deleteFollowingCharacter(characterId: Int)
    suspend fun getFollowingCharacter(id: Int): FollowingCharacterEntity?
    suspend fun isCharacterFollowing(characterId: Int): Boolean
    fun observeIsCharacterFollowing(characterId: Int): Flow<Boolean>

}