package com.purenative.plumbus.core.domain.interactors

import com.purenative.plumbus.core.data.repositories.characters.CharactersRepositoryImpl
import com.purenative.plumbus.core.domain.Interactor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChangeCharacterFollowStatus(
    private val repository: CharactersRepositoryImpl
) : Interactor<ChangeCharacterFollowStatus.Params>() {
    override suspend fun doWork(params: Params) {
        withContext(Dispatchers.IO) {
            params.characterIds.forEach { characterId ->
                when (params.action) {
                    Action.TOGGLE -> {
                        if (repository.isCharacterFollowing(characterId)) {
                            unfollow(characterId)
                        } else {
                            follow(characterId)
                        }
                    }
                    Action.FOLLOW -> follow(characterId)
                    Action.UNFOLLOW -> unfollow(characterId)
                }
            }

        }
    }

    private suspend fun unfollow(characterId: Int) {
        repository.deleteFollowingCharacter(characterId)
    }

    private suspend fun follow(characterId: Int) {
        val character = repository.getCharacter(characterId)

        character?.let {
            repository.addFollowingCharacter(character.toFollowingCharacterEntity())
        }

    }

    data class Params(
        val characterIds: Collection<Int>,
        val action: Action,
    ) {
        constructor(characterId: Int, action: Action) : this(listOf(characterId), action)
    }

    enum class Action { FOLLOW, UNFOLLOW, TOGGLE }
}