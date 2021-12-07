package com.purenative.plumbus.features.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.purenative.plumbus.R
import com.purenative.plumbus.core.domain.models.characters.Character

@Composable
fun PosterCard(
    character: Character,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Card(modifier = modifier) {
        Box(
            modifier = if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier
        ) {
            // TODO: remove text if the image has loaded (and animated in).
            // https://github.com/chrisbanes/accompanist/issues/76
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.CenterStart)
                )
            }
//            if (poster != null) {
//                Image(
//                    painter = rememberImagePainter(poster) {
//                        crossfade(true)
//                    },
//                    contentDescription = stringResource(
//                        R.string.cd_show_poster_image,
//                        character.name
//
//                    ),
//                    modifier = Modifier.matchParentSize(),
//                    contentScale = ContentScale.Crop,
//                )
//            }
        }
    }
}

@Composable
fun PlaceholderPosterCard(
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Box {
            // TODO: display something better
        }
    }
}
