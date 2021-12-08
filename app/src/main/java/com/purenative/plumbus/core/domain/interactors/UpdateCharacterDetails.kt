package com.purenative.plumbus.core.domain.interactors

import com.purenative.plumbus.core.data.repositories.characters.CharactersRepositoryImpl
import com.purenative.plumbus.core.domain.Interactor

class UpdateCharacterDetails(
    private val charactersRepository: CharactersRepositoryImpl
): Interactor<UpdateCharacterDetails.Params>() {
    override suspend fun doWork(params: Params) {
        charactersRepository.updateCharacter(params.characterId)
    }

    data class Params(val characterId: Int)
}