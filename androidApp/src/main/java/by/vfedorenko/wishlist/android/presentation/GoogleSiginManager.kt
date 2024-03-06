package by.vfedorenko.wishlist.android.presentation

import android.content.Intent
import by.vfedorenko.wishlist.domain.googlelogin.GoogleUser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


// TODO probs DI?
object GoogleSignInManager {

    suspend fun proceedGoogleSignIn(resultIntent: Intent): GoogleUser {
        val googleUser = getGoogleUser(resultIntent)
        val token = getFirebaseToken(googleUser.token)

        return googleUser.copy(token = token)
    }

    private suspend fun getGoogleUser(resultIntent: Intent): GoogleUser =
        suspendCancellableCoroutine { continuation ->
            GoogleSignIn.getSignedInAccountFromIntent(resultIntent)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        with(task.result) {
                            continuation.resume(
                                GoogleUser(
                                    token = idToken.orEmpty(),
                                    displayName = displayName.orEmpty(),
                                    profilePicUrl = photoUrl?.toString()
                                )
                            )
                        }
                    } else {
                        continuation.resumeWithException(RuntimeException("Failed to get Google token"))
                    }
                }
        }

    private suspend fun getFirebaseToken(googleToken: String): String =
        suspendCancellableCoroutine { continuation ->
            val auth = Firebase.auth
            auth.signInWithCredential(GoogleAuthProvider.getCredential(googleToken, null))
                .addOnCompleteListener { task1 ->
                    if (task1.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Firebase.auth.currentUser?.getIdToken(false)
                            ?.addOnCompleteListener { task2 ->
                                if (task2.isSuccessful) {
                                    continuation.resume(task2.result.token.orEmpty())
                                } else {
                                    continuation.resumeWithException(
                                        task2.exception ?: RuntimeException("Failed to get Firebase token")
                                    )
                                }
                            }

                    } else {
                        continuation.resumeWithException(
                            task1.exception ?: RuntimeException("Failed to sign in with google")
                        )
                    }
                }
        }
}