package by.vfedorenko.wishlist.android.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import by.vfedorenko.wishlist.presentation.navigation.BooleanArg
import by.vfedorenko.wishlist.presentation.navigation.IntArg
import by.vfedorenko.wishlist.presentation.navigation.NavigationDirection
import by.vfedorenko.wishlist.presentation.navigation.Screen
import by.vfedorenko.wishlist.presentation.navigation.StringArg

fun NavGraphBuilder.buildDestination(screen: Screen, body: @Composable (NavBackStackEntry) -> Unit) {
    Log.d("11!!", "route = ${screen.buildRoute()}, arguments = ${screen.buildArguments()}")
    composable(
        route = screen.buildRoute(),
        arguments = screen.buildArguments()
    ) {
        body(it)
    }
}

fun NavigationDirection.buildDestination(): String =
    if (arguments.isEmpty()) {
        screen.route
    } else {
        arguments.entries.joinToString(
            prefix = "${screen.route}/",
            separator = "/"
        ) { "${it.value}" }
    }


private fun Screen.buildRoute(): String =
    if (argumentKeys.isEmpty()) {
        route
    } else {
        argumentKeys.joinToString(
            prefix = "$route/",
            separator = "/"
        ) { "{${it.key}}" }
    }

private fun Screen.buildArguments(): List<NamedNavArgument> =
    argumentKeys
        .map {
            navArgument(it.key) {
                when(it) {
                    is BooleanArg -> NavType.BoolType
                    is IntArg -> NavType.IntType
                    is StringArg -> NavType.StringType
                }
            }
        }
