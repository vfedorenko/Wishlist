import SwiftUI
import shared

struct LoginScreen: View {

    @ObservedObject var viewModel = ViewModel<LoginState, LoginStore>()
    
    var body: some View {
        VStack {
            Text("Login screen \(viewModel.state.value)")
            Button {
                viewModel.acceptIntent(intent: LoginIntentLogin())
            } label: {
                Text("To List")
            }
        }
        .task {
            await viewModel.activate()
        }
    }
}

struct LoginScreen_Previews: PreviewProvider {
    static var previews: some View {
        LoginScreen()
    }
}
