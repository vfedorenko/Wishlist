import SwiftUI

struct ErrorScreen: View {
    let text: String
    
    var body: some View {
        Text(text)
    }
}

struct ErrorScreen_Previews: PreviewProvider {
    static var previews: some View {
        ErrorScreen(text: "test error")
    }
}
