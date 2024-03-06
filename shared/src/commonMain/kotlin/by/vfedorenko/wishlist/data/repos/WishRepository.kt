package by.vfedorenko.wishlist.data.repos

import by.vfedorenko.wishlist.data.network.WishesRemoteDataSource
import by.vfedorenko.wishlist.data.network.entities.parseId
import by.vfedorenko.wishlist.domain.entities.Wish
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WishRepository(
    private val wishesRemoteDataSource: WishesRemoteDataSource
) {
    private val _wishes = MutableStateFlow<List<Wish>>(emptyList())
    val wishes = _wishes.asStateFlow()

    fun createWish(wish: Wish) {
        _wishes.update { it + wish }
    }

    suspend fun getWishes() = wishesRemoteDataSource.getWishes()
        .map {
            Wish(
                id = it.name.parseId(),
                name = it.fields.name.stringValue
            )
        }
}
