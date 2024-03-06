package by.vfedorenko.wishlist.android.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.rememberNavController
import by.vfedorenko.wishlist.android.domain.GoogleAuthImpl
import by.vfedorenko.wishlist.android.presentation.compose.theme.AppTheme
import by.vfedorenko.wishlist.android.presentation.navigation.AppNavHost
import by.vfedorenko.wishlist.android.presentation.navigation.buildDestination
import by.vfedorenko.wishlist.presentation.navigation.Back
import by.vfedorenko.wishlist.presentation.navigation.CleanForward
import by.vfedorenko.wishlist.presentation.navigation.Forward
import by.vfedorenko.wishlist.presentation.navigation.NavigationManager
import by.vfedorenko.wishlist.presentation.navigation.Replace
import by.vfedorenko.wishlist.presentation.navigation.Screen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var googleAuthImpl: GoogleAuthImpl

    private var deferredSignInResult: CompletableDeferred<Intent?>? = null
    private val googleSignIn = registerForActivityResult(StartActivityForResult()) {
        deferredSignInResult?.complete(it.data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        googleAuthImpl.onInvoke = {
            // TODO move to secrets
            val serverId = "258677109801-bqpfrqdnv1qejnn27rgmkobd1q7n3h6t.apps.googleusercontent.com"

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(serverId)
                .requestEmail()
                .build()

            val client = GoogleSignIn.getClient(this, gso)

            deferredSignInResult = CompletableDeferred()
            googleSignIn.launch(client.signInIntent)
            val result = deferredSignInResult?.await()
            result?.let { GoogleSignInManager.proceedGoogleSignIn(it) }
        }

        setContent {
            AppTheme {
                Surface {
                    val navController = rememberNavController()

//                    var dialogCommand by remember { mutableStateOf<OpenAlertDialog?>(null) }
//                    dialogCommand?.let {
//                        AppDialog(
//                            dialog = it.dialog,
//                            onPositiveButtonClick = {
//                                it.onPositiveButtonClick()
//                                dialogCommand = null
//                            },
//                            onNegativeButtonClick = {
//                                it.onNegativeButtonClick()
//                                dialogCommand = null
//                            },
//                            onDismiss = it.onDismiss
//                        )
//                    }

                    LaunchedEffect(Unit) {
                        navigationManager.commands
                            .onEach { command ->
                                if (navController.currentDestination?.route != command.route.buildDestination()) {
                                    try {
                                        when (command) {
                                            Back -> if (navController.previousBackStackEntry != null) {
                                                navController.popBackStack()
                                            } else {
                                                finish()
                                            }

                                            is CleanForward -> {
                                                navController.clearBackStack()
                                                navController.navigate(command.route.buildDestination()) {
                                                    navController.clearSelf(this)
                                                    launchSingleTop = command.singleTop
                                                }
                                            }

                                            is Forward -> navController.navigate(command.route.buildDestination()) {
                                                launchSingleTop = command.singleTop
                                            }

                                            is Replace -> navController.navigate(command.route.buildDestination()) {
                                                navController.clearSelf(this)
                                                launchSingleTop = command.singleTop
                                            }

//                                            is OpenAlertDialog -> {
//                                                dialogCommand = command
//                                            }
//
//                                            is OpenBrowser -> openBrowser(command.url)
//                                            is OpenEmail -> openSystemMail(command.email)
//                                            is OpenMap -> openSystemMap(command.address)
//                                            is OpenPhoneCaller -> openSystemPhone(command.number)
//
//                                            is SelectPdf -> coroutineScope.launch {
//                                                command.callback(selectPdf())
//                                            }
//
//                                            is ShowMessage -> {
//                                                val message =
//                                                    command.messageRes?.let { getString(it) }
//                                                        ?: command.message
//                                                Toast.makeText(
//                                                    this@MainActivity,
//                                                    message,
//                                                    Toast.LENGTH_LONG
//                                                ).show()
//                                            }
                                        }
                                    } catch (e: Exception) {
//                                        Napier.e(e, "Failed to consume navigation command")
                                    }
                                }
                            }
                            .launchIn(this)
                    }

                    AppNavHost(
                        navController = navController,
                        startDestination = Screen.Login.route,
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        googleAuthImpl.onInvoke = null
    }

    private suspend fun firebaseAuthWithGoogle(idToken: String) = suspendCancellableCoroutine {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val auth = Firebase.auth
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    it.resume(auth.currentUser)
                } else {
                    // If sign in fails, display a message to the user.
                    it.resumeWithException(IllegalStateException("Authentication Failed."))
                }
            }
    }

    private fun NavHostController.clearBackStack() {
        while (previousBackStackEntry != null) {
            popBackStack()
        }
    }

    private fun NavHostController.clearSelf(options: NavOptionsBuilder) {
        currentDestination?.route?.let {
            options.popUpTo(it) { inclusive = true }
        }
    }
}
