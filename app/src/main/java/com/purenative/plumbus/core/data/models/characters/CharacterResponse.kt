package com.purenative.plumbus.core.data.models.characters

import com.google.gson.annotations.SerializedName
import com.purenative.plumbus.core.data.entities.CharacterEntity

data class CharacterResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String
) {
    fun toCharacterEntity() = CharacterEntity(
        id = id,
        name = name,
        image = image
    )
}