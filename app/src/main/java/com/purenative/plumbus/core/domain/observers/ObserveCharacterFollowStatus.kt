package com.purenative.plumbus.core.domain.observers

import com.purenative.plumbus.core.data.repositories.characters.CharactersRepositoryImpl
import com.purenative.plumbus.core.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow

class ObserveCharacterFollowStatus(
    private val repository: CharactersRepositoryImpl
) : SubjectInteractor<ObserveCharacterFollowStatus.Params, Boolean>() {

    override fun createObservable(params: Params): Flow<Boolean> {
        return repository.observeIsCharacterFollowing(params.characterId)
    }

    data class Params(val characterId: Int)
}