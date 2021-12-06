package com.purenative.plumbus.features.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purenative.plumbus.core.domain.models.characters.Character
import com.purenative.plumbus.core.domain.observers.ObserveCharacters
import com.purenative.plumbus.core.ui.ObservableLoadingCounter
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class CharactersViewModel(observeCharacters: ObserveCharacters): ViewModel() {
    private val charactersLoadingState = ObservableLoadingCounter()

    private val pendingActions = MutableSharedFlow<CharactersAction>()

    val state: StateFlow<CharactersViewState> = combine(
        observeCharacters.flow,
        charactersLoadingState.observable
    ) { characters, refreshing ->
        CharactersViewState(
            characters = characters,
            refreshing = refreshing
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CharactersViewState.EMPTY,
    )

    init {
        observeCharacters(Unit)

        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    CharactersAction.RefreshAction -> refresh(true)
                }
            }

        }
    }

    private fun refresh(fromUser: Boolean) {
        viewModelScope.launch {
        }
    }

    fun submitAction(action: CharactersAction) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }
}