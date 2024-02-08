import Swinject
import shared

final class LoginAssembly: Assembly {
    
    func assemble(container: Container) {
        container.register(LoginStore.self) { resolver in
            return LoginStore(
                initialState: defaultLoginState,
                reducer: LoginReducer(),
                middleware: LoginMiddleware(navigationManager: resolver.resolve(NavigationManager.self)!),
                commonMiddlewares: resolver.resolve(Set<AnyHashable>.self)!
            )
        }
    }
}
