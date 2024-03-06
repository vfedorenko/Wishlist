package by.vfedorenko.wishlist.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import by.vfedorenko.wishlist.android.presentation.screens.login.LoginScreen
import by.vfedorenko.wishlist.android.presentation.screens.wishes.editor.WishEditorScreen
import by.vfedorenko.wishlist.android.presentation.screens.wishes.list.WishListScreen
import by.vfedorenko.wishlist.presentation.navigation.Screen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String
) {
    val screens = remember { Screen.entries }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        screens.map { screen ->
            buildDestination(screen) {
                when (screen) {
                    Screen.Stub -> {
                        // skip stub
                    }
                    Screen.Login -> LoginScreen(hiltViewModel(it))
                    Screen.WishList -> WishListScreen(hiltViewModel(it))
                    Screen.WishEditor -> WishEditorScreen(hiltViewModel(it))
                }
            }
        }
    }
}
