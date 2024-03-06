package by.vfedorenko.wishlist.domain.googlelogin

data class GoogleUser(
    val token: String,
    val displayName: String = "",
    val profilePicUrl: String? = null,
)
