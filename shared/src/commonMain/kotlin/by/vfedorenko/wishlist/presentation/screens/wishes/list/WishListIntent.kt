package by.vfedorenko.wishlist.presentation.screens.wishes.list

import by.vfedorenko.wishlist.domain.entities.Wish
import by.vfedorenko.wishlist.presentation.MviIntent

sealed interface WishListIntent : MviIntent {
    data object OnAddClick : WishListIntent

    data class WishesReady(val value: List<Wish>) : WishListIntent
}
