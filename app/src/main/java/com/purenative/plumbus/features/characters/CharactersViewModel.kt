package com.purenative.plumbus.features.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.purenative.plumbus.core.domain.models.characters.Character
import com.purenative.plumbus.core.domain.observers.ObserveCharacters
import com.purenative.plumbus.core.ui.ObservableLoadingCounter
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class CharactersViewModel(observeCharacters: ObserveCharacters): ViewModel() {

    companion object {
        val PAGING_CONFIG = PagingConfig(
            pageSize = 20,
            initialLoadSize = 20
        )
    }

    private val pendingActions = MutableSharedFlow<CharactersAction>()

    val pagedList: Flow<PagingData<Character>> =
        observeCharacters.flow.cachedIn(viewModelScope)

    init {
        observeCharacters(ObserveCharacters.Params(PAGING_CONFIG))

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