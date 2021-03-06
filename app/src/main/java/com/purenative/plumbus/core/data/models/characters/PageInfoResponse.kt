package com.purenative.plumbus.core.data.models.characters

import com.google.gson.annotations.SerializedName

data class PageInfoResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("prev")
    val prev: String?
)