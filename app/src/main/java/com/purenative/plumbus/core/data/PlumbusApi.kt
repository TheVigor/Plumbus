package com.purenative.plumbus.core.data

import com.purenative.plumbus.core.data.models.characters.CharacterResponse
import com.purenative.plumbus.core.data.models.characters.PageResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlumbusApi {

    @GET("/api/character")
    suspend fun getCharacters(@Query("page") page: Int): Response<PageResponse<CharacterResponse>>

    @GET("/api/character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<CharacterResponse>
}