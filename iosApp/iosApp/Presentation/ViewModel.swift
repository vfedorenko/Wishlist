import Foundation
import shared

@MainActor
final class ViewModel<State : MviState, Store : MviStore<State>>: ObservableObject {

    @Published
    private(set) var state: State

    private var store: Store = DIContainer.shared.resolve()

    init() {
        state = store.initialState
        store.doInit(coroutineScope: iosCoroutineScope)
    }

    func activate() async {
        for await state in store.state {
            self.state = state
        }
    }

    func acceptIntent(intent: MviIntent) {
        store.acceptIntent(intent: intent)
    }
}
