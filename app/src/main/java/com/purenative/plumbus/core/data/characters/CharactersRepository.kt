package com.purenative.plumbus.core.data.characters

import com.purenative.plumbus.core.data.PlumbusApi

class CharactersRepository(private val plumbusApi: PlumbusApi) {
    suspend fun getCharacters(): List<CharacterResponse> {
        val response = plumbusApi.getCharacters()
        if (response.isSuccessful) {
            return response.body()?.results ?: emptyList()
        }
        return emptyList()
    }
}