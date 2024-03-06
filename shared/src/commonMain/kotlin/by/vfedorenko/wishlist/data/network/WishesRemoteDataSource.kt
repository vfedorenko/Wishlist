package by.vfedorenko.wishlist.data.network

import by.vfedorenko.wishlist.data.network.entities.NetworkResponse
import by.vfedorenko.wishlist.data.network.entities.NetworkResponseDocument
import by.vfedorenko.wishlist.data.network.entities.RemoteWish
import by.vfedorenko.wishlist.data.network.resources.WishResource
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import io.ktor.utils.io.core.*

class WishesRemoteDataSource(
    private val httpClientBuilder: ClientBuilder
) {

    suspend fun getWishes(): List<NetworkResponseDocument<RemoteWish>> {
        httpClientBuilder.build().use { client ->
            val response: NetworkResponse<RemoteWish> = client.get(WishResource()).body()
            return response.documents
        }
    }
}
