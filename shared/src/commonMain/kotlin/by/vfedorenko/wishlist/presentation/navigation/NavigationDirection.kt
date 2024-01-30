package by.vfedorenko.wishlist.presentation.navigation

const val NO_ID = "NO_ID"

sealed class NavArgument(open val key: String)
data class StringArg(override val key: String) : NavArgument(key)
data class IntArg(override val key: String) : NavArgument(key)
data class BooleanArg(override val key: String) : NavArgument(key)

enum class Screen(
    val route: String,
    val argumentKeys: List<NavArgument> = emptyList()
) {
    Stub("stub"),
    Login("login"),
    WishList("wish_list"),
    WishEditor(
        route = "wish_editor",
        argumentKeys = listOf(StringArg("wish_id"))
    )
}

sealed class NavigationDirection(val screen: Screen) {
    open val arguments: Map<NavArgument, Any> = emptyMap()

    data object Stub : NavigationDirection(Screen.Stub)
    data object Login : NavigationDirection(Screen.Login)
    data object WishList : NavigationDirection(Screen.WishList)

    data class WishEditor(val id: String = NO_ID) : NavigationDirection(Screen.WishEditor) {
        override val arguments = mapOf(
            Screen.WishEditor.argumentKeys[0] to id
        )
    }
}
