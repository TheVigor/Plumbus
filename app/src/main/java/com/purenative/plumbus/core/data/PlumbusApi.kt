package com.purenative.plumbus.core.data

import com.purenative.plumbus.core.data.models.characters.CharacterResponse
import com.purenative.plumbus.core.data.models.characters.PageResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface PlumbusApi {

    @GET("/api/character")
    suspend fun getCharacters(): PageResponse<CharacterResponse>
}