import Swinject
import shared

final class WishEditorAssembly: Assembly {
    
    func assemble(container: Container) {
        container.register(WishEditorStore.self) { resolver in
            return WishEditorStore(
                initialState: defaultWishEditorState,
                reducer: WishEditorReducer(),
                middleware: WishEditorMiddleware(
                    navigationManager: resolver.resolve(NavigationManager.self)!,
                    repository: resolver.resolve(WishRepository.self)!
                ),
                commonMiddlewares: resolver.resolve(Set<AnyHashable>.self)!
            )
        }
    }
}
