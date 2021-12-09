package com.purenative.plumbus.features.characterdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import com.purenative.plumbus.R
import com.purenative.plumbus.core.extensions.rememberFlowWithLifecycle
import com.purenative.plumbus.core.ui.Layout
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CharacterDetails(
    characterId: Int,
    navigateUp: () -> Unit
) {
    CharacterDetails(
        viewModel = getViewModel{ parametersOf(characterId) },
        navigateUp = navigateUp,
    )
}


@Composable
fun CharacterDetails(
    viewModel: CharacterDetailsViewModel,
    navigateUp: () -> Unit,
) {
    val viewState by rememberFlowWithLifecycle(viewModel.state).collectAsState(null)
    viewState?.let { state ->
        CharacterDetails(viewState = state) { action ->
            when (action) {
                CharacterDetailsAction.NavigateUp -> navigateUp()
                else -> viewModel.submitAction(action)
            }
        }
    }
}

@Composable
fun CharacterDetails(
    viewState: CharacterDetailsViewState,
    actioner: (CharacterDetailsAction) -> Unit,
) {
    Scaffold(
        topBar = {
            CharacterDetailsAppBar(
                backgroundColor = Color.Transparent,
                isRefreshing = viewState.refreshing,
                actioner = actioner,
                elevation = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
            )
        },
        floatingActionButton = {

            ToggleShowFollowFloatingActionButton(
                isFollowed = viewState.isFollowed,
                expanded = { false },
                onClick = { actioner(CharacterDetailsAction.FollowShowToggleAction) },
            )
        },
        snackbarHost = { snackBarHostState ->
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = { snackBarData ->
                    SwipeDismissSnackbar(
                        data = snackBarData,
                        onDismiss = { actioner(CharacterDetailsAction.ClearError) }
                    )
                },
                modifier = Modifier
                    .padding(horizontal = Layout.bodyMargin)
                    .fillMaxWidth()
            )
        }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {
            Image(
                painter = rememberImagePainter(viewState.character.image) {
                    crossfade(true)
                },
                contentDescription = stringResource(
                    R.string.cd_show_poster_image,
                    viewState.character.name

                ),
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = viewState.character.name,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun CharacterDetailsAppBar(
    backgroundColor: Color,
    isRefreshing: Boolean,
    actioner: (CharacterDetailsAction) -> Unit,
    elevation: Dp,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = { actioner(CharacterDetailsAction.NavigateUp) }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.cd_close),
                )
            }
        },
        actions = {
            if (isRefreshing) {
                AutoSizedCircularProgressIndicator(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxHeight()
                        .padding(14.dp)
                )
            } else {
                IconButton(onClick = { actioner(CharacterDetailsAction.RefreshAction(true)) }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = stringResource(R.string.cd_refresh)
                    )
                }
            }
        },
        elevation = elevation,
        backgroundColor = backgroundColor,
        modifier = modifier
    )
}

