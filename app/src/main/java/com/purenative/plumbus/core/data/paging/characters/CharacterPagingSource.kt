package com.purenative.plumbus.core.data.paging.characters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.purenative.plumbus.core.data.repositories.characters.CharactersRepositoryImpl
import com.purenative.plumbus.core.domain.models.characters.Character
import retrofit2.HttpException
import java.io.IOException

private const val CHARACTERS_PAGE_START_INDEX = 1

class CharactersPagingSource(
    private val charactersRepositoryImpl: CharactersRepositoryImpl
) : PagingSource<Int, Character>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: CHARACTERS_PAGE_START_INDEX
        return try {
            val response = charactersRepositoryImpl.getCharactersPage(page)

            val characters = response?.results?.map {
                charactersRepositoryImpl.responseMapper.map(it)
            } ?: emptyList()

            val nextKey = if (response == null) {
                null
            } else {
                if (response.info.next == null) null else page + 1
            }
            LoadResult.Page(
                data = characters,
                prevKey = if (page == CHARACTERS_PAGE_START_INDEX) null else page - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}