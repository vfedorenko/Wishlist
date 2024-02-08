import SwiftUI
import shared

struct WishListScreen: View {
    
    @ObservedObject var viewModel = ViewModel<WishListState, WishListStore>()

    var body: some View {
        VStack {
            ForEach(viewModel.state.wishes, id: \.id) { wish in
                Text(wish.name)
            }
            
            Button {
                viewModel.acceptIntent(intent: WishListIntentOnAddClick())
            } label: {
                Text("Add a Wish")
            }
        }
        .task {
            await viewModel.activate()
        }
    }
}

struct WishListScreen_Previews: PreviewProvider {
    static var previews: some View {
        WishListScreen()
    }
}
