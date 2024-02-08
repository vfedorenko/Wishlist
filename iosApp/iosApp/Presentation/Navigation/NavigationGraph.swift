import SwiftUI
import shared

struct NavigationGraph: View {
    
    @ObservedObject
    var router = Router()

    var body: some View {
        NavigationStack(path: $router.navPath) {
            LoginScreen()
                .navigationDestination(for: NavigationRoute.self) { direction in
                    switch direction {
                    case is NavigationRoute.WishList:
                        WishListScreen()
                        
                    case let wishEditor as NavigationRoute.WishEditor:
                        WishEditorScreen(wishId: wishEditor.id)
                        
                    default:
                        ErrorScreen(text: "No route was found for the direction \(direction)")
                    }
                }
        }
        .task {
            await router.activate()
        }
    }
}
