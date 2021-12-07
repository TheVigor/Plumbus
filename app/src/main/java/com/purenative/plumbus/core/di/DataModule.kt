package com.purenative.plumbus.core.di

import com.google.gson.GsonBuilder
import com.purenative.plumbus.core.data.PlumbusApi
import com.purenative.plumbus.core.data.paging.characters.CharactersPagingSource
import com.purenative.plumbus.core.data.repositories.characters.CharactersRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://rickandmortyapi.com"

fun dataModule() = module {
    single { createOkHttpClient() }
    single { createApi<PlumbusApi>(get()) }
    single { CharactersRepositoryImpl(get(), get()) }
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

inline fun <reified T> createApi(okHttpClient: OkHttpClient): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
        .build()
    return retrofit.create(T::class.java)
}
