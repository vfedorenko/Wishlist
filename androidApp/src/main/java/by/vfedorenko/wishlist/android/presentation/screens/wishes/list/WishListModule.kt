package by.vfedorenko.wishlist.android.presentation.screens.wishes.list

import by.vfedorenko.wishlist.data.repos.WishRepository
import by.vfedorenko.wishlist.presentation.Middleware
import by.vfedorenko.wishlist.presentation.MviState
import by.vfedorenko.wishlist.presentation.MviStore
import by.vfedorenko.wishlist.presentation.navigation.NavigationManager
import by.vfedorenko.wishlist.presentation.screens.wishes.list.WishListMiddleware
import by.vfedorenko.wishlist.presentation.screens.wishes.list.WishListReducer
import by.vfedorenko.wishlist.presentation.screens.wishes.list.WishListState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object WishListModule {

    @Provides
    @ViewModelScoped
    fun provideWishListMiddleware(
        navigationManager: NavigationManager,
        wishRepository: WishRepository
    ): Middleware<WishListState> = WishListMiddleware(
        navigationManager = navigationManager,
        repository = wishRepository
    )

    @Provides
    @ViewModelScoped
    fun provideWishListStore(
        middleware: Middleware<WishListState>,
        commonMiddlewares: Set<@JvmSuppressWildcards Middleware<MviState>>
    ): MviStore<WishListState> = MviStore(
        initialState = WishListState(),
        reducer = WishListReducer(),
        middleware = middleware,
        commonMiddlewares = commonMiddlewares
    )
}
