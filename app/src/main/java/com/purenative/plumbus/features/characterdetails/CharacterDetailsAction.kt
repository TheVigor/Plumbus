package com.purenative.plumbus.features.characterdetails

sealed class CharacterDetailsAction {
    data class RefreshAction(val fromUser: Boolean = true): CharacterDetailsAction()
    object FollowShowToggleAction: CharacterDetailsAction()
    object NavigateUp: CharacterDetailsAction()
    object ClearError: CharacterDetailsAction()
}