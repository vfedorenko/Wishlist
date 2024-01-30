package by.vfedorenko.wishlist.presentation.screens.wishes.list

import by.vfedorenko.wishlist.domain.entities.Wish
import by.vfedorenko.wishlist.presentation.MviState

data class WishListState(
    val wishes: List<Wish> = emptyList()
) : MviState
