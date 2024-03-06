package by.vfedorenko.wishlist.data.network.entities

import kotlinx.serialization.Serializable

@Serializable
data class RemoteWish(
    val name: RemoteStringValue
)
