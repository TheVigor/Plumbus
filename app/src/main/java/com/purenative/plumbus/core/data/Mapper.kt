package com.purenative.plumbus.core.data

interface Mapper<F, T> {
    suspend fun map(from: F): T
}