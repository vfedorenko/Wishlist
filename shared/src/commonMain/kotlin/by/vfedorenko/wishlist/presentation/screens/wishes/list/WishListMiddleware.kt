package by.vfedorenko.wishlist.presentation.screens.wishes.list

import by.vfedorenko.wishlist.data.repos.WishRepository
import by.vfedorenko.wishlist.presentation.GenericIntent
import by.vfedorenko.wishlist.presentation.Middleware
import by.vfedorenko.wishlist.presentation.MviIntent
import by.vfedorenko.wishlist.presentation.navigation.Forward
import by.vfedorenko.wishlist.presentation.navigation.NavigationManager
import by.vfedorenko.wishlist.presentation.navigation.NavigationRoute
import by.vfedorenko.wishlist.presentation.screens.wishes.list.WishListIntent.OnAddClick
import by.vfedorenko.wishlist.presentation.screens.wishes.list.WishListIntent.WishesReady
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow

class WishListMiddleware(
    private val navigationManager: NavigationManager,
    private val repository: WishRepository
) : Middleware<WishListState> {

    override fun execute(
        intent: MviIntent,
        state: WishListState,
        outputIntents: MutableSharedFlow<MviIntent>,
        coroutineScope: CoroutineScope
    ) {
        when(intent) {
            GenericIntent.Init -> coroutineScope.launch(outputIntents) {
                repository.wishes.collect {
                    Napier.d("WishesReady $it")
                    outputIntents.tryEmit(WishesReady(it))
                }
            }

            OnAddClick -> navigationManager.navigate(Forward(NavigationRoute.WishEditor()))
        }
    }
}
