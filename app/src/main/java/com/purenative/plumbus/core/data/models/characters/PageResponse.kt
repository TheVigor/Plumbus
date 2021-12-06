package com.purenative.plumbus.core.data.models.characters

import com.google.gson.annotations.SerializedName

data class PageResponse<T>(
    @SerializedName("info")
    val info: PageInfoResponse,
    @SerializedName("results")
    val results: List<T>
)
