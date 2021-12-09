package com.purenative.plumbus.features.following

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.purenative.plumbus.core.domain.models.characters.Character
import com.purenative.plumbus.core.domain.observers.ObserveFollowingCharacters
import com.purenative.plumbus.core.ui.ObservableLoadingCounter
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

internal class FollowingViewModel(
    private val observePagedFollowingCharacters: ObserveFollowingCharacters,
) : ViewModel() {

    companion object {
        private val PAGING_CONFIG = PagingConfig(
            pageSize = 20,
            initialLoadSize = 40,
        )
    }

    private val pendingActions = MutableSharedFlow<FollowingAction>()

    private val loadingState = ObservableLoadingCounter()

    val pagedList: Flow<PagingData<Character>> =
        observePagedFollowingCharacters.flow.cachedIn(viewModelScope)

    private val filter = MutableStateFlow<String?>(null)

    val state: StateFlow<FollowingViewState> = combine(
        loadingState.observable,
        filter,
    ) { loading, filter ->
        FollowingViewState(
            isLoading = loading,
            filter = filter,
            filterActive = !filter.isNullOrEmpty(),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FollowingViewState.EMPTY,
    )

    init {

        // When the filter and sort options change, update the data source
        viewModelScope.launch {
            filter.collect { updateDataSource() }
        }

        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    is FollowingAction.FilterCharacters -> setFilter(action.filter)
                }
            }
        }
    }

    private fun updateDataSource() {
        observePagedFollowingCharacters(
            ObserveFollowingCharacters.Params(
                filter = filter.value,
                pagingConfig = PAGING_CONFIG
            )
        )
    }

    private fun setFilter(filter: String?) {
        viewModelScope.launch {
            this@FollowingViewModel.filter.emit(filter)
        }
    }

    fun submitAction(action: FollowingAction) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }

}