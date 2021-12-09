package com.purenative.plumbus.core.data.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.purenative.plumbus.core.data.entities.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterEntity)

    @Query("SELECT * FROM characters")
    fun getCharacters(): PagingSource<Int, CharacterEntity>

    @Query("SELECT * FROM characters WHERE id=:id")
    fun observeCharacter(id: Int): Flow<CharacterEntity>

    @Query("SELECT * FROM characters WHERE id=:id")
    suspend fun getCharacter(id: Int): CharacterEntity?

    @Query("DELETE FROM characters")
    suspend fun clearCharacters()
}