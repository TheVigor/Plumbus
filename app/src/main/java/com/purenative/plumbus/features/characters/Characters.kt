package com.purenative.plumbus.features.characters

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.collectAsLazyPagingItems
import com.purenative.plumbus.R
import com.purenative.plumbus.core.extensions.rememberFlowWithLifecycle
import com.purenative.plumbus.core.ui.EntryGrid
import org.koin.androidx.compose.getViewModel

@Composable
fun Characters(
    openCharacterDetails: (characterId: Int) -> Unit,
    navigateUp: () -> Unit
) {
    Characters(
        viewModel = getViewModel(),
        openCharacterDetails = openCharacterDetails,
        navigateUp = navigateUp
    )
}

@Composable
internal fun Characters(
    viewModel: CharactersViewModel,
    openCharacterDetails: (characterId: Int) -> Unit,
    navigateUp: () -> Unit
) {
    EntryGrid(
        lazyPagingItems = rememberFlowWithLifecycle(viewModel.pagedList).collectAsLazyPagingItems(),
        title = stringResource(R.string.characters_title),
        onOpenShowDetails = openCharacterDetails,
        onNavigateUp = navigateUp,
    )
}



@Preview
@Composable
private fun PreviewCharacters() {
    RefreshButton(onClick = { /*TODO*/ })
}