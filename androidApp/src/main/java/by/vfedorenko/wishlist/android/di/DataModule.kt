package by.vfedorenko.wishlist.android.di

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
    fun provideWishRepository() = WishRepository()
}
