package com.purenative.plumbus.core.data.daos

import androidx.paging.PagingSource
import androidx.room.*
import com.purenative.plumbus.core.data.entities.FollowingCharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FollowingCharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<FollowingCharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: FollowingCharacterEntity)

    @Query("DELETE FROM following_characters WHERE id = :characterId")
    suspend fun delete(characterId: Int)

    @Query("SELECT * FROM following_characters")
    fun getFollowingCharacters(): PagingSource<Int, FollowingCharacterEntity>

    @Query("SELECT * FROM following_characters WHERE id=:id")
    fun getFollowingCharacter(id: Int): FollowingCharacterEntity?

    @Query("SELECT COUNT(*) FROM following_characters WHERE id=:characterId")
    suspend fun entryCountWithCharacterId(characterId: Int): Int

    @Query("SELECT COUNT(*) FROM following_characters WHERE id=:characterId")
    fun observeEntryCountWithCharacterId(characterId: Int): Flow<Int>

    @Query("DELETE FROM following_characters")
    suspend fun clearCharacters()
}