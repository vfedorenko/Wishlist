package by.vfedorenko.wishlist.android.presentation.screens.wishes.editor

import by.vfedorenko.wishlist.android.presentation.BaseViewModel
import by.vfedorenko.wishlist.presentation.MviStore
import by.vfedorenko.wishlist.presentation.screens.wishes.editor.WishEditorState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WishEditorViewModel @Inject constructor(
    store: MviStore<WishEditorState>
) : BaseViewModel<WishEditorState>(store)
