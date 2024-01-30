package by.vfedorenko.wishlist.android.presentation.screens.wishes.list

import by.vfedorenko.wishlist.android.presentation.BaseViewModel
import by.vfedorenko.wishlist.presentation.MviStore
import by.vfedorenko.wishlist.presentation.screens.wishes.list.WishListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    store: MviStore<WishListState>
) : BaseViewModel<WishListState>(store)
