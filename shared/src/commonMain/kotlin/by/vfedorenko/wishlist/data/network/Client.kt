package by.vfedorenko.wishlist.data.network

import by.vfedorenko.wishlist.data.local.SettingsManager
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.resources.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

private const val BASE_URL = "firestore.googleapis.com/v1/"
private const val PROJECT_ID = "projects/wishlist-62c12"
private const val DOCUMENTS_PATH = "/databases/(default)/documents"
private const val URL = BASE_URL + PROJECT_ID + DOCUMENTS_PATH

class ClientBuilder(
    private val settingsManager: SettingsManager
) {

    fun build() = HttpClient {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = URL
            }
        }

        expectSuccess = true

        install(Logging) {
            level = LogLevel.ALL
        }
        install(Resources)

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                }
            )
        }

        install(Auth) {
            if (settingsManager.accessToken.isNotBlank()) {
                bearer {
                    loadTokens {
                        BearerTokens(
                            accessToken = settingsManager.accessToken,
                            refreshToken = settingsManager.refreshToken
                        )
                    }

                    // TODO add refreshing
                }
            }

        }
    }
}
