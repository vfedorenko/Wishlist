package by.vfedorenko.wishlist.android.domain

import by.vfedorenko.wishlist.domain.googlelogin.GoogleAuth
import by.vfedorenko.wishlist.domain.googlelogin.GoogleUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleAuthImpl @Inject constructor() : GoogleAuth {

    var onInvoke: (suspend () -> GoogleUser?)? = null

    override suspend fun loginWithGoogle(): GoogleUser? = onInvoke?.invoke()
}
