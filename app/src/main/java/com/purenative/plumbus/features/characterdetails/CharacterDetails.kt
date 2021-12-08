package com.purenative.plumbus.features.characterdetails

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.purenative.plumbus.core.extensions.rememberFlowWithLifecycle
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
                CharacterDetailsAction.FollowShowToggleAction -> TODO()
                CharacterDetailsAction.NavigateUp -> navigateUp()
                is CharacterDetailsAction.RefreshAction -> TODO()
            }
        }
    }
}

@Composable
fun CharacterDetails(
    viewState: CharacterDetailsViewState,
    actioner: (CharacterDetailsAction) -> Unit,
) {
    Text(
        text = viewState.character.name,
        modifier = Modifier.fillMaxSize()
    )
}