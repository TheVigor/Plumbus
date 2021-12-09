package com.purenative.plumbus.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.purenative.plumbus.core.data.daos.CharacterDao
import com.purenative.plumbus.core.data.daos.CharacterRemoteKeysDao
import com.purenative.plumbus.core.data.daos.FollowingCharacterDao
import com.purenative.plumbus.core.data.entities.CharacterEntity
import com.purenative.plumbus.core.data.entities.CharacterRemoteKeysEntity
import com.purenative.plumbus.core.data.entities.FollowingCharacterEntity

@Database(
    entities = [CharacterEntity::class, CharacterRemoteKeysEntity::class, FollowingCharacterEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PlumbusDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharacterDao
    abstract fun charactersRemoteKeysDao(): CharacterRemoteKeysDao
    abstract fun followingCharactersDao(): FollowingCharacterDao
}
