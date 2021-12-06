package com.purenative.plumbus.core.data.mappers

interface Mapper<F, T> {
    suspend fun map(from: F): T
}

internal inline fun <F, T> Mapper<F, T>.forLists(): suspend (List<F>) -> List<T> {
    return { list -> list.map { item -> map(item) } }
}