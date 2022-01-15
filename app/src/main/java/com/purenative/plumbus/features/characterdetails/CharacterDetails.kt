package com.purenative.plumbus.features.characterdetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.compose.ui.util.lerp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.purenative.plumbus.R
import com.purenative.plumbus.core.domain.models.characters.Character
import com.purenative.plumbus.core.extensions.rememberFlowWithLifecycle
import com.purenative.plumbus.features.characters.CharacterSurface
import com.purenative.plumbus.features.characters.poster.CharacterImage
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.max
import kotlin.math.min

private val BottomBarHeight = 56.dp
private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = ImageOverlap + MinTitleOffset + GradientScroll
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)

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
    actioner: (CharacterDetailsAction) -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        val scroll = rememberScrollState(0)
        Header()
        Body(scroll)
        Title(viewState.character, scroll.value)
        Image(imageUrl = viewState.character.image, scroll = scroll.value)
        Up(actioner)
    }
}

@Composable
private fun Header() {
    Spacer(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color.Cyan, Color.Green
                    )
                )
            )
    )
}

@Composable
private fun Up(upPress: (CharacterDetailsAction) -> Unit) {
    IconButton(
        onClick = { upPress(CharacterDetailsAction.NavigateUp) },
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
            .background(
                color = Color.Black.copy(alpha = 0.32f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = null
        )
    }
}

@Composable
private fun Body(
    scroll: ScrollState
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(MinTitleOffset)
        )
        Column(
            modifier = Modifier.verticalScroll(scroll)
        ) {
            Spacer(Modifier.height(GradientScroll))
            CharacterSurface(Modifier.fillMaxWidth()) {
                Column {
                    Spacer(Modifier.height(ImageOverlap))
                    Spacer(Modifier.height(TitleHeight))

                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Detail Header",
                        style = MaterialTheme.typography.overline,
                        color = MaterialTheme.colors.primary,
                        modifier = HzPadding
                    )
                    Spacer(Modifier.height(16.dp))
                    var seeMore by remember { mutableStateOf(true) }
                    Text(
                        text = "Detail Placeholder",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.primary,
                        maxLines = if (seeMore) 5 else Int.MAX_VALUE,
                        overflow = TextOverflow.Ellipsis,
                        modifier = HzPadding
                    )
                    Spacer(
                        modifier = Modifier
                            .padding(bottom = BottomBarHeight)
                            .navigationBarsPadding(start = false, end = false)
                            .height(800.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun Title(character: Character, scroll: Int) {
    val maxOffset = with(LocalDensity.current) { MaxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }
    val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            .statusBarsPadding()
            .graphicsLayer { translationY = offset }
            .background(color = MaterialTheme.colors.background)
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = character.name,
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onSecondary,
            modifier = HzPadding
        )
        Text(
            text = character.name,
            style = MaterialTheme.typography.subtitle2,
            fontSize = 20.sp,
            color = MaterialTheme.colors.onSecondary,
            modifier = HzPadding
        )
        Spacer(Modifier.height(4.dp))
    }
}

@Composable
private fun Image(
    imageUrl: String,
    scroll: Int
) {
    val collapseRange = with(LocalDensity.current) { (MaxTitleOffset - MinTitleOffset).toPx() }
    val collapseFraction = (scroll / collapseRange).coerceIn(0f, 1f)

    CollapsingImageLayout(
        collapseFraction = collapseFraction,
        modifier = HzPadding.then(Modifier.statusBarsPadding())
    ) {
        CharacterImage(
            imageUrl = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CollapsingImageLayout(
    collapseFraction: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 1)

        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))

        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth) / 2, // centered when expanded
            constraints.maxWidth - imageWidth, // right aligned when collapsed
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = imageY + imageWidth
        ) {
            imagePlaceable.placeRelative(imageX, imageY)
        }
    }
}


//@Composable
//fun CharacterDetails(
//    viewState: CharacterDetailsViewState,
//    actioner: (CharacterDetailsAction) -> Unit,
//) {
//    Scaffold(
//        topBar = {
//            CharacterDetailsAppBar(
//                backgroundColor = Color.Transparent,
//                isRefreshing = viewState.refreshing,
//                actioner = actioner,
//                elevation = 0.dp,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .statusBarsPadding()
//            )
//        },
//        floatingActionButton = {
//
//            ToggleShowFollowFloatingActionButton(
//                isFollowed = viewState.isFollowed,
//                expanded = { false },
//                onClick = { actioner(CharacterDetailsAction.FollowShowToggleAction) },
//            )
//        },
//        snackbarHost = { snackBarHostState ->
//            SnackbarHost(
//                hostState = snackBarHostState,
//                snackbar = { snackBarData ->
//                    SwipeDismissSnackbar(
//                        data = snackBarData,
//                        onDismiss = { actioner(CharacterDetailsAction.ClearError) }
//                    )
//                },
//                modifier = Modifier
//                    .padding(horizontal = Layout.bodyMargin)
//                    .fillMaxWidth()
//            )
//        }
//    ) { contentPadding ->
//        Column(modifier = Modifier.padding(contentPadding)) {
//            Image(
//                painter = rememberImagePainter(viewState.character.image) {
//                    crossfade(true)
//                },
//                contentDescription = stringResource(
//                    R.string.cd_show_poster_image,
//                    viewState.character.name
//
//                ),
//                modifier = Modifier.fillMaxWidth(),
//                contentScale = ContentScale.Crop,
//            )
//            Text(
//                text = viewState.character.name,
//                style = MaterialTheme.typography.h6,
//                modifier = Modifier
//                    .padding(10.dp)
//                    .align(Alignment.CenterHorizontally)
//            )
//        }
//    }
//}

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

