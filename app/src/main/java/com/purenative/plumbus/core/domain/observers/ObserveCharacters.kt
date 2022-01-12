package com.purenative.plumbus.core.domain.observers

import androidx.paging.*
import com.purenative.plumbus.core.data.PlumbusDatabase
import com.purenative.plumbus.core.data.paging.characters.CharacterRemoteMediator
import com.purenative.plumbus.core.data.repositories.characters.CharactersRepositoryImpl
import com.purenative.plumbus.core.domain.PagingUseCase
import com.purenative.plumbus.core.domain.models.characters.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveCharacters(
    private val charactersRepositoryImpl: CharactersRepositoryImpl,
    private val plumbusDatabase: PlumbusDatabase
) : PagingUseCase<ObserveCharacters.Params, Character>() {
    @OptIn(ExperimentalPagingApi::class)
    override fun createObservable(
        params: Params
    ): Flow<PagingData<Character>> {
        return Pager(
            config = params.pagingConfig,
            remoteMediator = CharacterRemoteMediator(charactersRepositoryImpl, plumbusDatabase),
            pagingSourceFactory =  { plumbusDatabase.charactersDao().getCharacters() }
        ).flow.map { it.map { entity ->
            entity.toCharacter()
        } }
    }

    data class Params(
        override val pagingConfig: PagingConfig,
    ) : Parameters<Character>
}
