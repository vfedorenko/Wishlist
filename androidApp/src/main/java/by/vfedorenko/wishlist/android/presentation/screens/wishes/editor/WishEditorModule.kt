package by.vfedorenko.wishlist.android.presentation.screens.wishes.editor

import by.vfedorenko.wishlist.data.repos.WishRepository
import by.vfedorenko.wishlist.presentation.Middleware
import by.vfedorenko.wishlist.presentation.MviState
import by.vfedorenko.wishlist.presentation.MviStore
import by.vfedorenko.wishlist.presentation.navigation.NavigationManager
import by.vfedorenko.wishlist.presentation.screens.wishes.editor.WishEditorMiddleware
import by.vfedorenko.wishlist.presentation.screens.wishes.editor.WishEditorReducer
import by.vfedorenko.wishlist.presentation.screens.wishes.editor.WishEditorState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object WishEditorModule {

    @Provides
    @ViewModelScoped
    fun provideWishEditorMiddleware(
        navigationManager: NavigationManager,
        repository: WishRepository
    ): Middleware<WishEditorState> = WishEditorMiddleware(
        navigationManager = navigationManager,
        repository = repository
    )

    @Provides
    @ViewModelScoped
    fun provideWishEditorStore(
        middleware: Middleware<WishEditorState>,
        commonMiddlewares: Set<@JvmSuppressWildcards Middleware<MviState>>
    ): MviStore<WishEditorState> = MviStore(
        initialState = WishEditorState(),
        reducer = WishEditorReducer(),
        middleware = middleware,
        commonMiddlewares = commonMiddlewares
    )
}
