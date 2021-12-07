package com.purenative.plumbus.core.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.purenative.plumbus.core.data.entities.CharacterRemoteKeysEntity

@Dao
interface CharacterRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<CharacterRemoteKeysEntity>)

    @Query("SELECT * FROM character_remote_keys WHERE characterId = :characterId")
    suspend fun remoteKeysCharacterId(characterId: Int): CharacterRemoteKeysEntity?

    @Query("DELETE FROM character_remote_keys")
    suspend fun clearRemoteKeys()
}