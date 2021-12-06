package com.purenative.plumbus.features.characters

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.purenative.plumbus.core.extensions.rememberFlowWithLifecycle
import org.koin.androidx.compose.getViewModel
import timber.log.Timber

@Composable
fun Characters() {
    Characters(
        viewModel = getViewModel()
    )
}

@Composable
internal fun Characters(
    viewModel: CharactersViewModel
) {
    val viewState by rememberFlowWithLifecycle(viewModel.state).collectAsState(CharactersViewState.EMPTY)
    Timber.d("777: ${viewState.characters.size}")
    Characters(viewState = viewState)
}

@Composable
internal fun Characters(
    viewState: CharactersViewState
) {
    Text(text = viewState.characters.size.toString(), modifier = Modifier.fillMaxWidth())
}


@Preview
@Composable
private fun PreviewCharacters() {
    Characters(
        viewState = CharactersViewState.EMPTY
    )
}