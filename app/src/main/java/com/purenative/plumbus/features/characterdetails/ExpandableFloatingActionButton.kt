package com.purenative.plumbus.features.characterdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.purenative.plumbus.R

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ExpandableFloatingActionButton(
    text: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    backgroundColor: Color = MaterialTheme.colors.secondary,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    expanded: Boolean = true
) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = backgroundColor,
        elevation = elevation,
        contentColor = contentColor,
        shape = shape,
        modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AnimatedVisibility(visible = expanded) {
                Spacer(Modifier.width(20.dp))
            }

            icon()

            AnimatedVisibility(visible = expanded) {
                Box(Modifier.padding(start = 12.dp, end = 20.dp)) {
                    text()
                }
            }
        }
    }
}

@Composable
fun ToggleShowFollowFloatingActionButton(
    isFollowed: Boolean,
    onClick: () -> Unit,
    expanded: () -> Boolean,
    modifier: Modifier = Modifier,
) {

    ExpandableFloatingActionButton(
        onClick = onClick,
        icon = {
            Icon(
                imageVector = when {
                    isFollowed -> Icons.Default.Favorite
                    else -> Icons.Default.FavoriteBorder
                },
                contentDescription = when {
                    isFollowed -> stringResource(R.string.cd_follow_character_remove)
                    else -> stringResource(R.string.cd_follow_character_add)
                }
            )
        },
        text = {
            Text(
                when {
                    isFollowed -> stringResource(R.string.cd_follow_character_remove)
                    else -> stringResource(R.string.cd_follow_character_add)
                }
            )
        },
        backgroundColor = when {
            isFollowed -> MaterialTheme.colors.primary
            else -> MaterialTheme.colors.surface
        },
        expanded = expanded(),
        modifier = modifier
    )
}
