package com.purenative.plumbus.features.characterdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purenative.plumbus.core.base.InvokeStatus
import com.purenative.plumbus.core.domain.interactors.UpdateCharacterDetails
import com.purenative.plumbus.core.domain.models.characters.Character
import com.purenative.plumbus.core.domain.observers.ObserveCharacterDetails
import com.purenative.plumbus.core.ui.ObservableLoadingCounter
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val characterId: Int,
    private val updateCharacterDetails: UpdateCharacterDetails,
    observeCharacterDetails: ObserveCharacterDetails

): ViewModel() {
    private val loadingState = ObservableLoadingCounter()
    private val pendingActions = MutableSharedFlow<CharacterDetailsAction>()

    val state = combine(
        observeCharacterDetails.flow,
        flowOf(false),
        flowOf(false)
    ) { character, isFollowed, refreshing ->
        CharacterDetailsViewState(
            character = character,
            isFollowed = isFollowed,
            refreshing = refreshing
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CharacterDetailsViewState.EMPTY,
    )

    init {
        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    CharacterDetailsAction.FollowShowToggleAction -> TODO()
                    CharacterDetailsAction.NavigateUp -> TODO()
                    is CharacterDetailsAction.RefreshAction -> TODO()
                }
            }
        }

        observeCharacterDetails(ObserveCharacterDetails.Params(characterId))

        refresh()
    }

    private fun refresh() {
        updateCharacterDetails(UpdateCharacterDetails.Params(characterId = characterId))
    }

    private fun Flow<InvokeStatus>.watchStatus() = viewModelScope.launch { collectStatus() }

    private suspend fun Flow<InvokeStatus>.collectStatus() = collect { status ->
        when (status) {
            InvokeStatus.InvokeStarted -> loadingState.addLoader()
            InvokeStatus.InvokeSuccess -> loadingState.removeLoader()
            is InvokeStatus.InvokeError -> {
                //logger.i(status.throwable)
                //snackbarManager.addError(UiError(status.throwable))
                loadingState.removeLoader()
            }
        }
    }

    fun submitAction(action: CharacterDetailsAction) {
        viewModelScope.launch { pendingActions.emit(action) }
    }
}