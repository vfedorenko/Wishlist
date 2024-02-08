import Foundation
import SwiftUI
import shared


final class Router: ObservableObject {

    @Published
    var navPath = NavigationPath()
    
    @Inject
    private var navigationManager: NavigationManager

    @MainActor
    func activate() async {
        for await direction in navigationManager.commands {
            switch onEnum(of: direction) {
            
            case .forward(let forward):
                navPath.append(forward.route)
            
            case .back(_):
                navPath.removeLast()
            
            case .replace(let forward):
                navPath.removeLast()
                navPath.append(forward.route)
            
            case .cleanForward(let forward):
                navPath.removeLast(navPath.count)
                navPath.append(forward.route)
            }
        }
    }
}
