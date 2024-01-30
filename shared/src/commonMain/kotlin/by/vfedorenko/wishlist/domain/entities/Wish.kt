package by.vfedorenko.wishlist.domain.entities

import by.vfedorenko.wishlist.presentation.navigation.NO_ID

data class Wish(
    val id: String,
    val name: String
) {
    companion object {
        val empty = Wish(
            id = NO_ID,
            name = ""
        )
    }
}
