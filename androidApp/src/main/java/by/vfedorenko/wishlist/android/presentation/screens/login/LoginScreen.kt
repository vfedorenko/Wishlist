package by.vfedorenko.wishlist.android.presentation.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import by.vfedorenko.wishlist.presentation.screens.login.LoginIntent
import by.vfedorenko.wishlist.presentation.screens.login.LoginState

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val state by viewModel.state.collectAsState()

    LoginContent(
        state = state,
        onIntent = viewModel::acceptIntent
    )
}

@Composable
private fun LoginContent(
    state: LoginState,
    onIntent: (LoginIntent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = state.value)
        Button(onClick = { onIntent(LoginIntent.Login) }) {
            Text(text = "Login")
        }
    }
}
