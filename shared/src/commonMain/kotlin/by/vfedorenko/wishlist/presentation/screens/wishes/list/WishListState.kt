package by.vfedorenko.wishlist.presentation.screens.wishes.list

import by.vfedorenko.wishlist.domain.entities.Wish
import by.vfedorenko.wishlist.presentation.Middleware
import by.vfedorenko.wishlist.presentation.MviState
import by.vfedorenko.wishlist.presentation.MviStore
import by.vfedorenko.wishlist.presentation.Reducer

data class WishListState(
    val wishes: List<Wish> = emptyList()
) : MviState

// iOS compatibility, probs move to commonIos ?
val defaultWishListState = WishListState()
class WishListStore(
    initialState: WishListState,
    reducer: Reducer<WishListState>,
    middleware: Middleware<WishListState>,
    commonMiddlewares: Set<Middleware<MviState>>,
) : MviStore<WishListState>(
    initialState = initialState,
    reducer = reducer,
    middleware = middleware,
    commonMiddlewares = commonMiddlewares,
)
