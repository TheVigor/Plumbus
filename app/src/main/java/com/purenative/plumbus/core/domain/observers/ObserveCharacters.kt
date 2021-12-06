package com.purenative.plumbus.core.domain.observers

import com.purenative.plumbus.core.data.repositories.characters.CharactersRepositoryImpl
import com.purenative.plumbus.core.domain.SubjectInteractor
import com.purenative.plumbus.core.domain.models.characters.Character
import kotlinx.coroutines.flow.Flow

class ObserveCharacters(
    private val charactersRepositoryImpl: CharactersRepositoryImpl
): SubjectInteractor<Unit, List<Character>>() {
    override fun createObservable(params: Unit): Flow<List<Character>> {
        return charactersRepositoryImpl.observeCharacters()
    }
}