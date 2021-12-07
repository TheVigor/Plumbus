package com.purenative.plumbus.core.data.mappers.characters

import com.purenative.plumbus.core.data.entities.CharacterEntity
import com.purenative.plumbus.core.data.mappers.Mapper
import com.purenative.plumbus.core.domain.models.characters.Character

class CharacterToCharacterEntityMapper: Mapper<Character, CharacterEntity> {
    override suspend fun map(from: Character) = CharacterEntity(
        id = from.id,
        name = from.name,
        image = from.image
    )
}