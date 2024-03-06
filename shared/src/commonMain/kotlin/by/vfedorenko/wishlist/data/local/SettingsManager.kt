package by.vfedorenko.wishlist.data.local

import com.russhwolf.settings.Settings
import com.russhwolf.settings.string

class SettingsManager(
    private val settings: Settings
) {

    var accessToken by settings.string(defaultValue = "")
    var refreshToken by settings.string(defaultValue = "")

    fun clear() {
        settings.clear()
    }
}
