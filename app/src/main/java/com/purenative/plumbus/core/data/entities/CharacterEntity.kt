package com.purenative.plumbus.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.purenative.plumbus.core.domain.models.characters.Character

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "species") val species: String?,
    @ColumnInfo(name = "gender") val gender: String?,
    @ColumnInfo(name = "status") val status: String?
) {
    fun toFollowingCharacterEntity() = FollowingCharacterEntity(
        id = id,
        name = name,
        image = image,
        species = species,
        gender = gender,
        status = status
    )

    fun toCharacter() = Character(
        id = id,
        name = name.orEmpty(),
        image = image.orEmpty(),
        species = species.orEmpty(),
        gender = gender.orEmpty(),
        status = status.orEmpty()
    )
}