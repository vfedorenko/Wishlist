package by.vfedorenko.wishlist.android.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.rememberNavController
import by.vfedorenko.wishlist.android.presentation.compose.theme.AppTheme
import by.vfedorenko.wishlist.android.presentation.navigation.AppNavHost
import by.vfedorenko.wishlist.android.presentation.navigation.buildDestination
import by.vfedorenko.wishlist.presentation.navigation.Back
import by.vfedorenko.wishlist.presentation.navigation.BackTo
import by.vfedorenko.wishlist.presentation.navigation.CleanForward
import by.vfedorenko.wishlist.presentation.navigation.Forward
import by.vfedorenko.wishlist.presentation.navigation.NavigationManager
import by.vfedorenko.wishlist.presentation.navigation.Replace
import by.vfedorenko.wishlist.presentation.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                                if (navController.currentDestination?.route != command.direction.buildDestination()) {
                                    try {
                                        when (command) {
                                            Back -> if (navController.previousBackStackEntry != null) {
                                                navController.popBackStack()
                                            } else {
                                                finish()
                                            }

                                            is BackTo -> navController.popBackStack(
                                                route = command.direction.buildDestination(),
                                                inclusive = false
                                            )

                                            is CleanForward -> {
                                                navController.clearBackStack()
                                                navController.navigate(command.direction.buildDestination()) {
                                                    navController.clearSelf(this)
                                                    launchSingleTop = command.singleTop
                                                }
                                            }

                                            is Forward -> navController.navigate(command.direction.buildDestination()) {
                                                launchSingleTop = command.singleTop
                                            }

                                            is Replace -> navController.navigate(command.direction.buildDestination()) {
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
