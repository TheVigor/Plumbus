package com.purenative.plumbus.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_remote_keys")
data class CharacterRemoteKeysEntity(
    @PrimaryKey
    val characterId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)