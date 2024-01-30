package by.vfedorenko.wishlist.presentation.navigation

sealed class NavigationCommand(val direction: NavigationDirection = NavigationDirection.Stub)

data object Back : NavigationCommand()
class Forward(directions: NavigationDirection, val singleTop: Boolean = false) : NavigationCommand(directions)
class CleanForward(directions: NavigationDirection, val singleTop: Boolean = false) : NavigationCommand(directions)
class Replace(directions: NavigationDirection, val singleTop: Boolean = false) : NavigationCommand(directions)
class BackTo(directions: NavigationDirection) : NavigationCommand(directions)
