package by.vfedorenko.wishlist.presentation.screens.wishes.list

import by.vfedorenko.wishlist.presentation.MviIntent
import by.vfedorenko.wishlist.presentation.Reducer
import by.vfedorenko.wishlist.presentation.screens.wishes.list.WishListIntent.WishesReady

class WishListReducer : Reducer<WishListState> {

    override fun reduce(state: WishListState, intent: MviIntent) = when (intent) {
        is WishesReady -> state.copy(wishes = intent.value)
        else -> state
    }
}
