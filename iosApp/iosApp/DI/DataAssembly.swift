import Swinject
import shared

final class DataAssembly: Assembly {
    
    func assemble(container: Container) {
        container.register(WishRepository.self) { _ in
            WishRepository()
        }.inObjectScope(.container)
    }
}
