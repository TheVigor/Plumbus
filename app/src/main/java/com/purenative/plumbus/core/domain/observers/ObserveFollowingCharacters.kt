package com.purenative.plumbus.core.domain.observers

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.purenative.plumbus.core.data.repositories.characters.CharactersRepositoryImpl
import com.purenative.plumbus.core.domain.PagingInteractor
import com.purenative.plumbus.core.domain.models.characters.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveFollowingCharacters(
    private val repository: CharactersRepositoryImpl
) : PagingInteractor<ObserveFollowingCharacters.Params, Character>() {

    override fun createObservable(
        params: Params
    ): Flow<PagingData<Character>> = Pager(config = params.pagingConfig) {
        repository.getPagedFollowingCharacters()
    }.flow.map { it.map { entity -> entity.toCharacter() } }

    data class Params(
        val filter: String? = null,
        override val pagingConfig: PagingConfig
    ) : Parameters<Character>
}