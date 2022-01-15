package com.purenative.plumbus.core.domain.models.characters

import com.purenative.plumbus.core.data.entities.CharacterEntity
import com.purenative.plumbus.core.data.entities.FollowingCharacterEntity

data class Character(
    val id: Int,
    val name: String,
    val image: String,
    val species: String,
    val gender: String,
    val status: String
) {
    companion object {
        val EMPTY = Character(
            id = 0,
            name = "",
            image = "",
            species = "",
            gender = "",
            status = ""
        )
    }

    fun toFollowingCharacterEntity() = FollowingCharacterEntity(
        id = id,
        name = name,
        image = image,
        species = species,
        gender = gender,
        status = status
    )

    fun toCharacterEntity() = CharacterEntity(
        id = id,
        name = name,
        image = image,
        species = species,
        gender = gender,
        status = status
    )

}