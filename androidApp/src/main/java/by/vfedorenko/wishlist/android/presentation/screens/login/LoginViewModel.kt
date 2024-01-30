package by.vfedorenko.wishlist.android.presentation.screens.login

import by.vfedorenko.wishlist.android.presentation.BaseViewModel
import by.vfedorenko.wishlist.presentation.MviStore
import by.vfedorenko.wishlist.presentation.screens.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    store: MviStore<LoginState>
) : BaseViewModel<LoginState>(store)
