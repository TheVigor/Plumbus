package com.purenative.plumbus.core.data.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.purenative.plumbus.core.data.entities.CharacterEntity

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<CharacterEntity>)

    @Query("SELECT * FROM characters")
    fun getCharacters(): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM characters")
    suspend fun clearCharacters()
}