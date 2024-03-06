package by.vfedorenko.wishlist.data.network.entities

import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponse<T>(
    val documents: List<NetworkResponseDocument<T>>
)

@Serializable
data class NetworkResponseDocument<T>(
    val name: String,
    val fields: T,
    val createTime: String,
    val updateTime: String
)

@Serializable
data class RemoteStringValue(
    val stringValue: String
)

fun String.parseId(): String = split("/").lastOrNull().orEmpty()
