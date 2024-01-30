package by.vfedorenko.wishlist.android.presentation.screens.wishes.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.vfedorenko.wishlist.presentation.screens.wishes.list.WishListIntent
import by.vfedorenko.wishlist.presentation.screens.wishes.list.WishListState

@Composable
fun WishListScreen(viewModel: WishListViewModel) {
    val state by viewModel.state.collectAsState()

    WishListContent(
        state = state,
        onIntent = viewModel::acceptIntent
    )
}

@Composable
private fun WishListContent(
    state: WishListState,
    onIntent: (WishListIntent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(state.wishes, key = {it.id}) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = it.name
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Button(onClick = { onIntent(WishListIntent.OnAddClick) }) {
            Text(text = "Add Wish")
        }
    }
}
