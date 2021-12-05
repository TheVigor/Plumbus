package com.purenative.plumbus.core.data

import com.purenative.plumbus.core.data.characters.CharacterResponse
import com.purenative.plumbus.core.data.characters.PageResponse
import retrofit2.Response
import retrofit2.http.GET

interface PlumbusApi {

    @GET("/api/character")
    suspend fun getCharacters(): Response<PageResponse<CharacterResponse>>
}