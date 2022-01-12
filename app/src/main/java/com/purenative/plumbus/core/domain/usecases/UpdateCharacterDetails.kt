package com.purenative.plumbus.core.domain.usecases

import com.purenative.plumbus.core.data.repositories.characters.CharactersRepositoryImpl
import com.purenative.plumbus.core.domain.UseCase

class UpdateCharacterDetails(
    private val charactersRepository: CharactersRepositoryImpl
): UseCase<UpdateCharacterDetails.Params>() {
    override suspend fun doWork(params: Params) {
        charactersRepository.updateCharacter(params.characterId)
    }

    data class Params(val characterId: Int)
}