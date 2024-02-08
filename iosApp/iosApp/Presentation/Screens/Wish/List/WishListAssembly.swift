import Swinject
import shared

final class WishListAssembly: Assembly {
    
    func assemble(container: Container) {
        container.register(WishListStore.self) { resolver in
            return WishListStore(
                initialState: defaultWishListState,
                reducer: WishListReducer(),
                middleware: WishListMiddleware(
                    navigationManager: resolver.resolve(NavigationManager.self)!,
                    repository: resolver.resolve(WishRepository.self)!
                ),
                commonMiddlewares: resolver.resolve(Set<AnyHashable>.self)!
            )
        }
    }
}
