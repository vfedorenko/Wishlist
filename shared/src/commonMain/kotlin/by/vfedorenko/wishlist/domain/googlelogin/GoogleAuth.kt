package by.vfedorenko.wishlist.domain.googlelogin

interface GoogleAuth {
    suspend fun loginWithGoogle(): GoogleUser?
}