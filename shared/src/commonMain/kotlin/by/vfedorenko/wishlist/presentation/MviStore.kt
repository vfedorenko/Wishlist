package by.vfedorenko.wishlist.presentation

import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.cancellation.CancellationException

interface MviIntent
interface MviState

sealed interface GenericIntent : MviIntent {
    data object Init : GenericIntent
    data object OnCleared : GenericIntent

    data class Loading(val isLoading: Boolean) : GenericIntent
    data class Failure(
        val error: Throwable,
        val showAlert: Boolean = true
    ) : GenericIntent
}

interface Reducer<State : MviState> {
    fun reduce(state: State, intent: MviIntent): State
}

interface Middleware<State : MviState> {
    fun execute(
        intent: MviIntent,
        state: State,
        outputIntents: MutableSharedFlow<MviIntent>,
        coroutineScope: CoroutineScope
    )

    fun genericExceptionHandler(
        outputIntents: MutableSharedFlow<MviIntent>,
        showError: Boolean = true
    ) = CoroutineExceptionHandler { _, exception ->
        if (exception !is CancellationException) {
            outputIntents.tryEmit(GenericIntent.Failure(exception, showError))
        }
    }

    fun CoroutineScope.launch(
        output: MutableSharedFlow<MviIntent>,
        handleErrors: Boolean = true,
        showAlert: Boolean = true,
        handleLoading: Boolean = true,
        body: suspend CoroutineScope.() -> Unit
    ) {
        launch(
            context = if (handleErrors) genericExceptionHandler(
                outputIntents = output,
                showError = showAlert
            ) else EmptyCoroutineContext
        ) {
            if (handleLoading) output.tryEmit(GenericIntent.Loading(true))
            body()
            if (handleLoading) output.tryEmit(GenericIntent.Loading(false))
        }
    }
}

open class MviStore<State : MviState>(
    val initialState: State, // It is public for the iOS compatibility
    private val reducer: Reducer<State>,
    private val middleware: Middleware<State>,
    private val commonMiddlewares: Set<Middleware<MviState>>
) {

    val state = MutableStateFlow(initialState)
    private val intents = MutableSharedFlow<MviIntent>(extraBufferCapacity = 100)

    fun init(coroutineScope: CoroutineScope) {
        intents
            .onSubscription { intents.emit(GenericIntent.Init) }
            .onCompletion {
                executeIntent(GenericIntent.OnCleared, coroutineScope)
                state.value = initialState
            }
            .onEach { executeIntent(it, coroutineScope) }
            .launchIn(coroutineScope)
    }

    fun acceptIntent(intent: MviIntent) {
        intents.tryEmit(intent)
    }

    private fun executeIntent(intent: MviIntent, coroutineScope: CoroutineScope) {
        Napier.i("ACTION: $intent", tag = "ACTION")

        state.update { old ->
            reducer.reduce(old, intent)
        }

        middleware.execute(intent, state.value, intents, coroutineScope)

        commonMiddlewares.forEach {
            it.execute(intent, state.value, intents, coroutineScope)
        }
    }
}

inline fun <reified T> MviIntent.tryCast(block: T.() -> Unit) {
    if (this is T) block()
}

private fun generateActionLogs(intent: MviIntent, state: MviState) =
    "ACTION: $intent"

val iosCoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)