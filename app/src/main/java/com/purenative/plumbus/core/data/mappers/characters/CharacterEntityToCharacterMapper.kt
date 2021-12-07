package com.purenative.plumbus.core.data.mappers.characters

import com.purenative.plumbus.core.data.entities.CharacterEntity
import com.purenative.plumbus.core.data.mappers.Mapper
import com.purenative.plumbus.core.domain.models.characters.Character

class CharacterEntityToCharacterMapper: Mapper<CharacterEntity, Character> {
    override suspend fun map(from: CharacterEntity) = Character(
        id = from.id,
        name = from.name ?: "",
        image = from.image ?: ""
    )
}