package by.vfedorenko.wishlist.android.presentation.screens.wishes.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.vfedorenko.wishlist.presentation.screens.wishes.editor.WishEditorIntent
import by.vfedorenko.wishlist.presentation.screens.wishes.editor.WishEditorState

@Composable
fun WishEditorScreen(viewModel: WishEditorViewModel) {
    val state by viewModel.state.collectAsState()

    WishEditorContent(
        state = state,
        onIntent = viewModel::acceptIntent
    )
}

@Composable
private fun WishEditorContent(
    state: WishEditorState,
    onIntent: (WishEditorIntent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.name,
            onValueChange = { onIntent(WishEditorIntent.OnNameChanged(it)) }
        )

        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = { onIntent(WishEditorIntent.OnSaveClick) }
        ) {
            Text(text = "Save")
        }
    }
}
