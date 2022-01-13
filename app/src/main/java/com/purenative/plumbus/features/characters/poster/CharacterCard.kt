package com.purenative.plumbus.features.characters.poster

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.purenative.plumbus.core.domain.models.characters.Character
import com.purenative.plumbus.features.characters.CharacterSurface

@Composable
fun CharacterCard(
    character: Character,
    onCharacterClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    CharacterSurface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .clickable(onClick = { onCharacterClicked() })
                .fillMaxSize()
        ) {
            CharacterImage(
                imageUrl = character.image,
                elevation = 4.dp,
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = character.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle1,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun PlaceholderCharacterCard(
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Box {
        }
    }
}


