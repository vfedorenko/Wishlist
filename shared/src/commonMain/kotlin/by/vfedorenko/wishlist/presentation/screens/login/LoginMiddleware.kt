package by.vfedorenko.wishlist.presentation.screens.login

import by.vfedorenko.wishlist.data.repos.UserSessionRepository
import by.vfedorenko.wishlist.presentation.GenericIntent
import by.vfedorenko.wishlist.presentation.Middleware
import by.vfedorenko.wishlist.presentation.MviIntent
import by.vfedorenko.wishlist.presentation.navigation.Forward
import by.vfedorenko.wishlist.presentation.navigation.NavigationManager
import by.vfedorenko.wishlist.presentation.navigation.NavigationRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow

class LoginMiddleware(
    private val userSessionRepository: UserSessionRepository,
    private val navigationManager: NavigationManager
) : Middleware<LoginState> {

    override fun execute(
        intent: MviIntent,
        state: LoginState,
        outputIntents: MutableSharedFlow<MviIntent>,
        coroutineScope: CoroutineScope
    ) {
        when (intent) {
            GenericIntent.Init -> {
                if (userSessionRepository.userAuthorized()) {
                    navigationManager.navigate(Forward(NavigationRoute.WishList))
                }
            }

            LoginIntent.Login -> {
                coroutineScope.launch(outputIntents) {
                    if (userSessionRepository.signInWithGoogle()) {
                        navigationManager.navigate(Forward(NavigationRoute.WishList))
                    } else {
                        // TODO show error
                    }
                }
            }
        }
    }
}
