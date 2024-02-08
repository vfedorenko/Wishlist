import Swinject

final class ScreenAssembly: Assembly {

    func assemble(container: Container) {
        let assemblies: [Assembly] = [
            LoginAssembly(),
            WishListAssembly(),
            WishEditorAssembly(),
        ]
        assemblies.forEach { $0.assemble(container: container) }
    }
}
