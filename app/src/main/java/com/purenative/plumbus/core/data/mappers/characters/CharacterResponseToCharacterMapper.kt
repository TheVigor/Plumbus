package com.purenative.plumbus.core.data.mappers.characters

import com.purenative.plumbus.core.data.mappers.Mapper
import com.purenative.plumbus.core.data.models.characters.CharacterResponse
import com.purenative.plumbus.core.domain.models.characters.Character

class CharacterResponseToCharacterMapper: Mapper<CharacterResponse, Character> {
    override suspend fun map(from: CharacterResponse) = Character(
        id = from.id,
        name = from.name
    )
}