package com.purenative.plumbus.core.data.models.characters

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)