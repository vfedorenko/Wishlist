package by.vfedorenko.wishlist.presentation.screens.wishes.editor

import by.vfedorenko.wishlist.presentation.MviIntent
import by.vfedorenko.wishlist.presentation.Reducer
import by.vfedorenko.wishlist.presentation.screens.wishes.editor.WishEditorIntent.OnNameChanged
import by.vfedorenko.wishlist.presentation.screens.wishes.editor.WishEditorIntent.WishReady

class WishEditorReducer : Reducer<WishEditorState> {

    override fun reduce(state: WishEditorState, intent: MviIntent) = when (intent) {
        is WishReady -> state.copy(wish = intent.value)
        is OnNameChanged -> state.copy(name = intent.value)
        else -> state
    }
}
