import Swinject
import shared

final class AppAssembly: Assembly {
    
    func assemble(container: Container) {
        container.register(NavigationManager.self) { _ in
            return NavigationManager()
        }.inObjectScope(.container)
        
        // TODO should be Middleware generic
        container.register(Set<AnyHashable>.self) { _ in
            return commonMiddlewares
        }.inObjectScope(.container)
    }
}
