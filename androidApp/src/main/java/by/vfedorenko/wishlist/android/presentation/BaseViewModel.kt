package by.vfedorenko.wishlist.android.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.vfedorenko.wishlist.presentation.MviIntent
import by.vfedorenko.wishlist.presentation.MviState
import by.vfedorenko.wishlist.presentation.MviStore
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<State : MviState>(
    private val store: MviStore<State>
) : ViewModel() {

    val state = store.state.asStateFlow()

    init {
        store.init(viewModelScope)
    }

    fun acceptIntent(intent: MviIntent) {
        store.acceptIntent(intent)
    }
}
