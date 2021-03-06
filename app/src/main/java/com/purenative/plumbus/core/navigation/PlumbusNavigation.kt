package com.purenative.plumbus.core.navigation

import androidx.compose.animation.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.purenative.plumbus.features.characterdetails.CharacterDetails
import com.purenative.plumbus.features.characters.Characters
import com.purenative.plumbus.features.following.Following

const val CHARACTERS_TAG = "characters"
const val CHARACTER_TAG = "character"
const val FOLLOWING_TAG = "following"

sealed class Screen(val route: String) {
    object Characters: Screen(CHARACTERS_TAG)
    object Following: Screen(FOLLOWING_TAG)
}

private sealed class LeafScreen(
    private val route: String,
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Characters : LeafScreen(CHARACTERS_TAG)
    object Following : LeafScreen(FOLLOWING_TAG)

    object CharacterDetails : LeafScreen("$CHARACTER_TAG/{characterId}") {
        fun createRoute(root: Screen, characterId: Int): String {
            return "${root.route}/$CHARACTER_TAG/$characterId"
        }
    }
}

@ExperimentalAnimationApi
@Composable
internal fun PlumbusNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Characters.route,
        enterTransition = { defaultPlumbusEnterTransition(initialState, targetState) },
        exitTransition = { defaultPlumbusExitTransition(initialState, targetState) },
        popEnterTransition = { defaultPlumbusPopEnterTransition() },
        popExitTransition = { defaultPlumbusPopExitTransition() },
        modifier = modifier,
    ) {
        addCharactersTopLevel(navController)
        addFollowingTopLevel(navController)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addCharactersTopLevel(
    navController: NavController,
) {
    navigation(
        route = Screen.Characters.route,
        startDestination = LeafScreen.Characters.createRoute(Screen.Characters),
    ) {
        addCharacters(navController, Screen.Characters)
        addCharacterDetails(navController, Screen.Characters)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addFollowingTopLevel(
    navController: NavController,
) {
    navigation(
        route = Screen.Following.route,
        startDestination = LeafScreen.Following.createRoute(Screen.Following),
    ) {
        addFollowing(navController, Screen.Following)
        addCharacterDetails(navController, Screen.Following)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addCharacters(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.Characters.createRoute(root)) {
        Characters(
            openCharacterDetails = { characterId ->
                navController.navigate(LeafScreen.CharacterDetails.createRoute(root, characterId))
            },
            navigateUp = navController::navigateUp
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addFollowing(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.Following.createRoute(root)) {
        Following(
            openCharacterDetails = { characterId ->
                navController.navigate(LeafScreen.CharacterDetails.createRoute(root, characterId))
            },
        )
    }
}

const val CHARACTER_ID_ARG = "characterId"

@ExperimentalAnimationApi
private fun NavGraphBuilder.addCharacterDetails(
    navController: NavController,
    root: Screen,
) {
    composable(
        route = LeafScreen.CharacterDetails.createRoute(root),
        arguments = listOf(
            navArgument(CHARACTER_ID_ARG) { type = NavType.IntType }
        )
    ) {
        val characterId = it.arguments?.getInt(CHARACTER_ID_ARG)!!
        CharacterDetails(
            characterId = characterId,
            navigateUp = navController::navigateUp
        )
    }
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultPlumbusEnterTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): EnterTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    // If we're crossing nav graphs (bottom navigation graphs), we crossfade
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeIn()
    }
    // Otherwise we're in the same nav graph, we can imply a direction
    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.Start)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultPlumbusExitTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): ExitTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    // If we're crossing nav graphs (bottom navigation graphs), we crossfade
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeOut()
    }
    // Otherwise we're in the same nav graph, we can imply a direction
    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.Start)
}

private val NavDestination.hostNavGraph: NavGraph
    get() = hierarchy.first { it is NavGraph } as NavGraph

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultPlumbusPopEnterTransition(): EnterTransition {
    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.End)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultPlumbusPopExitTransition(): ExitTransition {
    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.End)
}