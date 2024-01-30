package by.vfedorenko.wishlist.android.presentation.screens.login

import by.vfedorenko.wishlist.presentation.Middleware
import by.vfedorenko.wishlist.presentation.MviState
import by.vfedorenko.wishlist.presentation.MviStore
import by.vfedorenko.wishlist.presentation.navigation.NavigationManager
import by.vfedorenko.wishlist.presentation.screens.login.LoginMiddleware
import by.vfedorenko.wishlist.presentation.screens.login.LoginReducer
import by.vfedorenko.wishlist.presentation.screens.login.LoginState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object LoginModule {

    @Provides
    @ViewModelScoped
    fun provideLoginMiddleware(
        navigationManager: NavigationManager
    ): Middleware<LoginState> = LoginMiddleware(
        navigationManager
    )

    @Provides
    @ViewModelScoped
    fun provideLoginStore(
        middleware: Middleware<LoginState>,
        commonMiddlewares: Set<@JvmSuppressWildcards Middleware<MviState>>
    ): MviStore<LoginState> = MviStore(
        initialState = LoginState(),
        reducer = LoginReducer(),
        middleware = middleware,
        commonMiddlewares = commonMiddlewares
    )
}