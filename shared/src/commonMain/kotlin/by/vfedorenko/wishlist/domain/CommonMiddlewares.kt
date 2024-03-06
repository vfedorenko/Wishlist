package by.vfedorenko.wishlist.domain

import by.vfedorenko.wishlist.presentation.GenericIntent
import by.vfedorenko.wishlist.presentation.Middleware
import by.vfedorenko.wishlist.presentation.MviIntent
import by.vfedorenko.wishlist.presentation.MviState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow

val commonMiddlewares = setOf<Middleware<MviState>>(
    ErrorHandlingMiddleware()
)

class ErrorHandlingMiddleware : Middleware<MviState> {
    override fun execute(
        intent: MviIntent,
        state: MviState,
        outputIntents: MutableSharedFlow<MviIntent>,
        coroutineScope: CoroutineScope
    ) {
        if (intent is GenericIntent.Failure) {
            Napier.e(
                message = "Got an error:",
                throwable = intent.error,
                tag = "11!!"
            )
            intent.error.printStackTrace()
        }
    }
}
