package by.vfedorenko.wishlist.android

import android.content.Context
import android.content.SharedPreferences
import by.vfedorenko.wishlist.data.local.SettingsManager
import by.vfedorenko.wishlist.domain.commonMiddlewares
import by.vfedorenko.wishlist.presentation.Middleware
import by.vfedorenko.wishlist.presentation.MviState
import by.vfedorenko.wishlist.presentation.navigation.NavigationManager
import com.russhwolf.settings.SharedPreferencesSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPrefs(@ApplicationContext appContext: Context): SharedPreferences =
        appContext.getSharedPreferences("APP_SHARED_PREFS", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSettingsManager(prefs: SharedPreferences) = SettingsManager(
        settings = SharedPreferencesSettings(prefs)
    )

    @Provides
    @Singleton
    fun provideNavigationManager() = NavigationManager()

    @Provides
    fun provideCommonMiddlewares() = commonMiddlewares
}
