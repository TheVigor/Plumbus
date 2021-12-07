package com.purenative.plumbus.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.purenative.plumbus.core.data.daos.CharacterDao
import com.purenative.plumbus.core.data.daos.CharacterRemoteKeysDao
import com.purenative.plumbus.core.data.entities.CharacterEntity
import com.purenative.plumbus.core.data.entities.CharacterRemoteKeysEntity

@Database(
    entities = [CharacterEntity::class, CharacterRemoteKeysEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PlumbusDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharacterDao
    abstract fun charactersRemoteKeysDao(): CharacterRemoteKeysDao
}
