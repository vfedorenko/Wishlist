package by.vfedorenko.wishlist.presentation.screens.wishes.editor

import by.vfedorenko.wishlist.data.repos.WishRepository
import by.vfedorenko.wishlist.domain.entities.Wish
import by.vfedorenko.wishlist.presentation.Middleware
import by.vfedorenko.wishlist.presentation.MviIntent
import by.vfedorenko.wishlist.presentation.navigation.Back
import by.vfedorenko.wishlist.presentation.navigation.NavigationManager
import by.vfedorenko.wishlist.presentation.screens.wishes.editor.WishEditorIntent.OnSaveClick
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.random.Random

class WishEditorMiddleware(
    private val navigationManager: NavigationManager,
    private val repository: WishRepository
) : Middleware<WishEditorState> {

    override fun execute(
        intent: MviIntent,
        state: WishEditorState,
        outputIntents: MutableSharedFlow<MviIntent>,
        coroutineScope: CoroutineScope
    ) {
        when (intent) {
            is OnSaveClick -> {
                repository.createWish(
                    Wish(
                        id = Random.nextLong().toString(),
                        name = state.name
                    )
                )

                navigationManager.navigate(Back)
            }
        }
    }
}
