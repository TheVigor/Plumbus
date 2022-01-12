package com.purenative.plumbus.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.purenative.plumbus.core.domain.models.characters.Character

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "image") val image: String?
) {
    fun toFollowingCharacterEntity() = FollowingCharacterEntity(
        id = id,
        name = name,
        image = image
    )

    fun toCharacter() = Character(
        id = id,
        name = name.orEmpty(),
        image = image.orEmpty()
    )
}