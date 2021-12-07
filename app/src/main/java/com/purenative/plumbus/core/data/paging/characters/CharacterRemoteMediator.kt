package com.purenative.plumbus.core.data.paging.characters

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.purenative.plumbus.core.data.PlumbusApi
import com.purenative.plumbus.core.data.PlumbusDatabase
import com.purenative.plumbus.core.data.entities.CharacterEntity
import com.purenative.plumbus.core.data.entities.CharacterRemoteKeysEntity
import com.purenative.plumbus.core.data.repositories.characters.CharactersRepositoryImpl
import com.purenative.plumbus.core.domain.models.characters.Character
import retrofit2.HttpException
import java.io.IOException

private const val CHARACTERS_PAGE_START_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
internal class CharacterRemoteMediator(
    private val charactersRepositoryImpl: CharactersRepositoryImpl,
    private val plumbusDatabase: PlumbusDatabase
) : RemoteMediator<Int, CharacterEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: CHARACTERS_PAGE_START_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        try {
            val response = charactersRepositoryImpl.getCharactersPage(page = page)

            val characters = response?.results?.map {
                charactersRepositoryImpl.responseMapper.map(it)
            } ?: emptyList()

            val endOfPaginationReached = characters.isEmpty()
            plumbusDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    plumbusDatabase.charactersRemoteKeysDao().clearRemoteKeys()
                    plumbusDatabase.charactersDao().clearCharacters()
                }
                val prevKey = if (page == CHARACTERS_PAGE_START_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = characters.map {
                    CharacterRemoteKeysEntity(characterId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                plumbusDatabase.charactersRemoteKeysDao().insertAll(keys)
                plumbusDatabase.charactersDao().insertAll(characters.map {
                    charactersRepositoryImpl.entityMapper.map(it) }
                )
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }

    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterEntity>
    ): CharacterRemoteKeysEntity? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character ->
                // Get the remote keys of the last item retrieved
                plumbusDatabase.charactersRemoteKeysDao().remoteKeysCharacterId(character.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharacterEntity>
    ): CharacterRemoteKeysEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character ->
                // Get the remote keys of the first items retrieved
                plumbusDatabase.charactersRemoteKeysDao().remoteKeysCharacterId(character.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CharacterEntity>
    ): CharacterRemoteKeysEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                plumbusDatabase.charactersRemoteKeysDao().remoteKeysCharacterId(id)
            }
        }
    }


}