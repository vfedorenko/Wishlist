package by.vfedorenko.wishlist.data.repos

import by.vfedorenko.wishlist.data.local.SettingsManager
import by.vfedorenko.wishlist.domain.googlelogin.GoogleAuth

class UserSessionRepository(
    private val googleAuth: GoogleAuth,
    private val settingsManager: SettingsManager
) {

    fun userAuthorized() = settingsManager.accessToken.isNotEmpty()

    suspend fun signInWithGoogle(): Boolean {
        googleAuth.loginWithGoogle()?.let {
            settingsManager.accessToken = it.token
            return true
        }

        return false
    }
}
