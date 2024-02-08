import SwiftUI
import shared

@main
struct iOSApp: App {
 
    init() {
        LogUtilsKt.debugBuild()
    }
    
	var body: some Scene {
		WindowGroup {
            NavigationGraph()
		}
	}
}
