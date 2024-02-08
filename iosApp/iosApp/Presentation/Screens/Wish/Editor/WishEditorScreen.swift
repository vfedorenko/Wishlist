import SwiftUI
import shared

struct WishEditorScreen: View {
    
    var wishId: String
    
    @ObservedObject var viewModel = ViewModel<WishEditorState, WishEditorStore>()

    var body: some View {
        VStack {
            TextInput(
                label: "Wish Name",
                text: viewModel.state.name
            ) { text in
                viewModel.acceptIntent(intent: WishEditorIntentOnNameChanged(value: text))
            }
            
            Button {
                viewModel.acceptIntent(intent: WishEditorIntentOnSaveClick())
            } label: {
                Text("Save")
            }
        }
        .task {
            await viewModel.activate()
        }
    }
}

struct TextInput: View {

    var label: String = ""
    let text: String
    let onTextChanged: (String) -> Void
    
    @State private var textBinding: String = ""

    var body: some View {
        TextField(
            label,
            text: $textBinding
        ).onChange(of: textBinding) { newValue in
            onTextChanged(newValue)
        }
    }
}
