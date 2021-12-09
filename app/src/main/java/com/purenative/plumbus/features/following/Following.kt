package com.purenative.plumbus.features.following

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.purenative.plumbus.R
import com.purenative.plumbus.core.domain.models.characters.Character
import com.purenative.plumbus.core.extensions.rememberFlowWithLifecycle
import com.purenative.plumbus.core.ui.Layout
import com.purenative.plumbus.core.ui.bodyWidth
import com.purenative.plumbus.core.ui.itemSpacer
import com.purenative.plumbus.core.ui.itemsInGrid
import com.purenative.plumbus.core.ui.theme.AppBarAlphas
import com.purenative.plumbus.features.characters.PosterCard
import org.koin.androidx.compose.getViewModel

@Composable
fun Following(
    openCharacterDetails: (characterId: Int) -> Unit,
) {
    Following(
        viewModel = getViewModel(),
        openCharacterDetails = openCharacterDetails
    )
}

@Composable
internal fun Following(
    viewModel: FollowingViewModel,
    openCharacterDetails: (characterId: Int) -> Unit
) {
    val viewState by rememberFlowWithLifecycle(viewModel.state)
        .collectAsState(initial = FollowingViewState.EMPTY)

    Following(
        state = viewState,
        list = rememberFlowWithLifecycle(viewModel.pagedList).collectAsLazyPagingItems()
    ) { action ->
        when (action) {
            is FollowingAction.OpenCharacterDetails -> openCharacterDetails(action.characterId)
            else -> viewModel.submitAction(action)
        }
    }
}

@Composable
internal fun Following(
    state: FollowingViewState,
    list: LazyPagingItems<Character>,
    actioner: (FollowingAction) -> Unit,
) {
    Scaffold(
        topBar = {
            FollowingAppBar(
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(state.isLoading),
            onRefresh = { actioner(FollowingAction.RefreshAction) },
            modifier = Modifier.bodyWidth(),
            indicatorPadding = paddingValues,
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    scale = true
                )
            }
        ) {
            val columns = Layout.columns
            val bodyMargin = Layout.bodyMargin
            val gutter = Layout.gutter

            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    FilterPanel(
                        filterHint = stringResource(R.string.filter, list.itemCount),
                        onFilterChanged = { actioner(FollowingAction.FilterCharacters(it)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                itemsInGrid(
                    lazyPagingItems = list,
                    columns = columns / 4,
                    // We minus 8.dp off the grid padding, as we use content padding on the items below
                    contentPadding = PaddingValues(
                        horizontal = (bodyMargin - 8.dp).coerceAtLeast(0.dp),
                        vertical = (gutter - 8.dp).coerceAtLeast(0.dp),
                    ),
                    verticalItemPadding = (gutter - 8.dp).coerceAtLeast(0.dp),
                    horizontalItemPadding = (gutter - 8.dp).coerceAtLeast(0.dp),
                ) { entry ->
                    if (entry != null) {
                        FollowingShowItem(
                            character = entry,
                            onClick = { actioner(FollowingAction.OpenCharacterDetails(entry.id)) },
                            contentPadding = PaddingValues(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        // TODO placeholder?
                    }
                }

                itemSpacer(16.dp)
            }
        }
    }
}

@Composable
private fun FilterPanel(
    filterHint: String,
    onFilterChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier.padding(horizontal = Layout.bodyMargin, vertical = Layout.gutter)) {
        var filter by remember { mutableStateOf(TextFieldValue()) }

        SearchTextField(
            value = filter,
            onValueChange = { value ->
                filter = value
                onFilterChanged(value.text)
            },
            hint = filterHint,
            modifier = Modifier.weight(1f)
        )

    }
}

@Composable
private fun FollowingShowItem(
    character: Character,
    onClick: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(contentPadding)
    ) {
        PosterCard(
            character = character,
            modifier = Modifier
                .fillMaxWidth(0.2f) // 20% of the width
                .aspectRatio(2 / 3f)
        )

        Spacer(Modifier.width(16.dp))

        Column {
            Text(
                text = character.name,
                style = MaterialTheme.typography.subtitle1,
            )

        }
    }
}

@Composable
private fun FollowingAppBar(
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface.copy(
            alpha = AppBarAlphas.translucentBarAlpha()
        ),
        contentColor = MaterialTheme.colors.onSurface,
        modifier = modifier,
        title = { Text(text = stringResource(R.string.following_characters_title)) }
    )
}