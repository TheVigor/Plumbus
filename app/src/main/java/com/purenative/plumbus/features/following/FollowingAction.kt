package com.purenative.plumbus.features.following

sealed class FollowingAction {
    object RefreshAction : FollowingAction()
    data class FilterCharacters(val filter: String = "") : FollowingAction()
    data class OpenCharacterDetails(val characterId: Int) : FollowingAction()
}