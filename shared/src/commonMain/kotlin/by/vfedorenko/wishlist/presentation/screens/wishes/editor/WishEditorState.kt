package by.vfedorenko.wishlist.presentation.screens.wishes.editor

import by.vfedorenko.wishlist.domain.entities.Wish
import by.vfedorenko.wishlist.presentation.MviState

data class WishEditorState(
    val wish: Wish = Wish.empty,
    val name: String = ""
) : MviState
