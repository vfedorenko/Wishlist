package by.vfedorenko.wishlist.android.di

import by.vfedorenko.wishlist.android.domain.GoogleAuthImpl
import by.vfedorenko.wishlist.data.local.SettingsManager
import by.vfedorenko.wishlist.data.network.ClientBuilder
import by.vfedorenko.wishlist.data.network.WishesRemoteDataSource
import by.vfedorenko.wishlist.data.repos.UserSessionRepository
import by.vfedorenko.wishlist.data.repos.WishRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideHttpClient(
        settingsManager: SettingsManager
    ): ClientBuilder = ClientBuilder(settingsManager)

    @Provides
    @Singleton
    fun provideWishesRemoteDataSource(
        clientBuilder: ClientBuilder
    ) = WishesRemoteDataSource(clientBuilder)


    @Provides
    @Singleton
    fun provideWishRepository(
        remoteDataSource: WishesRemoteDataSource
    ) = WishRepository(remoteDataSource)

    @Provides
    @Singleton
    fun provideUserSessionRepository(
        settingsManager: SettingsManager,
        googleAuthImpl: GoogleAuthImpl
    ) = UserSessionRepository(
        googleAuth = googleAuthImpl,
        settingsManager = settingsManager
    )
}
