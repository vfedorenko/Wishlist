package by.vfedorenko.wishlist.data.network

import by.vfedorenko.wishlist.data.network.resources.User
import io.ktor.client.*
import io.ktor.client.plugins.resources.*

class UsersRemoteDataSource(
    private val httpClient: HttpClient
) {

    suspend fun getCurrentUser() {
        val result = httpClient.get(User())
    }
}