package by.vfedorenko.wishlist.presentation.screens.login

import by.vfedorenko.wishlist.presentation.Middleware
import by.vfedorenko.wishlist.presentation.MviState
import by.vfedorenko.wishlist.presentation.MviStore
import by.vfedorenko.wishlist.presentation.Reducer

data class LoginState(
    val value: String = "Login"
) : MviState

// iOS compatibility, probs move to commonIos ?
val defaultLoginState = LoginState()
class LoginStore(
    initialState: LoginState,
    reducer: Reducer<LoginState>,
    middleware: Middleware<LoginState>,
    commonMiddlewares: Set<Middleware<MviState>>,
) : MviStore<LoginState>(
    initialState = initialState,
    reducer = reducer,
    middleware = middleware,
    commonMiddlewares = commonMiddlewares,
)
