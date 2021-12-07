package com.purenative.plumbus.core.domain.observers

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.purenative.plumbus.core.data.paging.characters.CharactersPagingSource
import com.purenative.plumbus.core.data.repositories.characters.CharactersRepositoryImpl
import com.purenative.plumbus.core.domain.PagingInteractor
import com.purenative.plumbus.core.domain.SubjectInteractor
import com.purenative.plumbus.core.domain.models.characters.Character
import kotlinx.coroutines.flow.Flow

class ObserveCharacters(
    private val charactersRepositoryImpl: CharactersRepositoryImpl
) : PagingInteractor<ObserveCharacters.Params, Character>() {
    override fun createObservable(
        params: Params
    ): Flow<PagingData<Character>> {
        return Pager(
            config = params.pagingConfig,
//            remoteMediator = PaginatedEntryRemoteMediator { page ->
//                updatePopularShows.executeSync(
//                    UpdatePopularShows.Params(page = page, forceRefresh = true)
//                )
//            },
            pagingSourceFactory = { CharactersPagingSource(charactersRepositoryImpl) }
        ).flow
    }

    data class Params(
        override val pagingConfig: PagingConfig,
    ) : Parameters<Character>
}
