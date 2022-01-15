package com.purenative.plumbus.core.data.models.characters

import com.google.gson.annotations.SerializedName
import com.purenative.plumbus.core.data.entities.CharacterEntity
import com.purenative.plumbus.core.domain.models.characters.Character

data class CharacterResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("status")
    val status: String
) {
    fun toCharacterEntity() = CharacterEntity(
        id = id,
        name = name,
        image = image,
        species = species,
        gender = gender,
        status = status
    )

    fun toCharacter() = Character(
        id = id,
        name = name,
        image = image,
        species = species,
        gender = gender,
        status = status
    )
}