package by.vfedorenko.wishlist.presentation.navigation

sealed class NavigationCommand(val route: NavigationRoute = NavigationRoute.Stub)

data object Back : NavigationCommand()
class Forward(directions: NavigationRoute, val singleTop: Boolean = false) : NavigationCommand(directions)
class CleanForward(directions: NavigationRoute, val singleTop: Boolean = false) : NavigationCommand(directions)
class Replace(directions: NavigationRoute, val singleTop: Boolean = false) : NavigationCommand(directions)
