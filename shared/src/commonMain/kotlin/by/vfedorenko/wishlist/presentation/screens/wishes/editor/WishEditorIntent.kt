package by.vfedorenko.wishlist.presentation.screens.wishes.editor

import by.vfedorenko.wishlist.domain.entities.Wish
import by.vfedorenko.wishlist.presentation.MviIntent

sealed interface WishEditorIntent : MviIntent {
    data class LoadWish(val value: String) : WishEditorIntent
    data class WishReady(val value: Wish) : WishEditorIntent
    data class OnNameChanged(val value: String) : WishEditorIntent

    data object OnSaveClick : WishEditorIntent
}
