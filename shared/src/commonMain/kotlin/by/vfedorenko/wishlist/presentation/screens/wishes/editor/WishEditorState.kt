package by.vfedorenko.wishlist.presentation.screens.wishes.editor

import by.vfedorenko.wishlist.domain.entities.Wish
import by.vfedorenko.wishlist.presentation.Middleware
import by.vfedorenko.wishlist.presentation.MviState
import by.vfedorenko.wishlist.presentation.MviStore
import by.vfedorenko.wishlist.presentation.Reducer

data class WishEditorState(
    val wish: Wish = Wish.empty,
    val name: String = ""
) : MviState

// iOS compatibility, probs move to commonIos ?
val defaultWishEditorState = WishEditorState()
class WishEditorStore(
    initialState: WishEditorState,
    reducer: Reducer<WishEditorState>,
    middleware: Middleware<WishEditorState>,
    commonMiddlewares: Set<Middleware<MviState>>,
) : MviStore<WishEditorState>(
    initialState = initialState,
    reducer = reducer,
    middleware = middleware,
    commonMiddlewares = commonMiddlewares,
)
