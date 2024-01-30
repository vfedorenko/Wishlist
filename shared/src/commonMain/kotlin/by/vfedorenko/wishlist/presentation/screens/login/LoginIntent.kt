package by.vfedorenko.wishlist.presentation.screens.login

import by.vfedorenko.wishlist.presentation.MviIntent

sealed interface LoginIntent : MviIntent {
    data object Login : LoginIntent
}
