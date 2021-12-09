package com.purenative.plumbus.features.following

data class FollowingViewState(
    val isLoading: Boolean = false,
    val filterActive: Boolean = false,
    val filter: String? = null,
) {
    companion object {
        val EMPTY = FollowingViewState()
    }
}
