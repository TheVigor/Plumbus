package com.purenative.plumbus.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.purenative.plumbus.core.domain.models.characters.Character

@Entity(tableName = "following_characters")
data class FollowingCharacterEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "image") val image: String?
) {
    fun toCharacter(): Character = Character(
        id = id,
        name = name ?: "",
        image = image ?: ""
    )
}