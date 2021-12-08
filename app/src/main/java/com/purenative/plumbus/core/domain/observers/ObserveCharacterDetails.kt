package com.purenative.plumbus.core.domain.observers

import com.purenative.plumbus.core.data.repositories.characters.CharactersRepositoryImpl
import com.purenative.plumbus.core.domain.SubjectInteractor
import com.purenative.plumbus.core.domain.models.characters.Character
import kotlinx.coroutines.flow.Flow

class ObserveCharacterDetails(
    private val charactersRepository: CharactersRepositoryImpl
): SubjectInteractor<ObserveCharacterDetails.Params, Character>() {

    override fun createObservable(params: Params): Flow<Character> {
        return charactersRepository.observerCharacter(params.characterId)
    }

    data class Params(val characterId: Int)
}