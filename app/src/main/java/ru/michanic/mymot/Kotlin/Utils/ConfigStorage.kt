package ru.michanic.mymot.Kotlin.Utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import ru.michanic.mymot.Kotlin.Models.SearchFilterConfig
import ru.michanic.mymot.Kotlin.MyMotApplication

class ConfigStorage(context: Context) {
    private val settings // = MyMotApplication.appContext.getSharedPreferences(PREFS_NAME, 0);
            : SharedPreferences
    var exteptedWords = emptyList<String>()
    var aboutText = ""

    init {
        settings = context.getSharedPreferences(PREFS_NAME, 0)
    }

    fun saveFilterConfig(filterConfig: SearchFilterConfig) {
        val editor = settings.edit()
        val location = filterConfig.selectedRegion
        if (location != null) {
            editor.putInt(LOCATION_ID, location.id)
        } else {
            editor.putInt(LOCATION_ID, 0)
        }
        val manufacturer = filterConfig.selectedManufacturer
        if (manufacturer != null) {
            editor.putInt(MANUFACTURER_ID, manufacturer.id)
        } else {
            editor.putInt(MANUFACTURER_ID, 0)
        }
        val model = filterConfig.selectedModel
        if (model != null) {
            editor.putInt(MODEL_ID, model.id)
        } else {
            editor.putInt(MODEL_ID, 0)
        }
        editor.putInt(PRICE_FROM, filterConfig.priceFrom)
        editor.putInt(PRICE_FOR, filterConfig.priceFor)
        editor.commit()
    }

    val filterConfig: SearchFilterConfig
        get() {
            val filterConfig = SearchFilterConfig()
            val selectedRegion = MyMotApplication.dataManager?.getRegionById(
                settings.getInt(
                    LOCATION_ID, 0
                )
            )
            if (selectedRegion != null) {
                filterConfig.selectedRegion = selectedRegion
            }
            val manufacturer = MyMotApplication.dataManager?.getManufacturerById(
                settings.getInt(
                    MANUFACTURER_ID, 0
                )
            )
            if (manufacturer != null) {
                filterConfig.selectedManufacturer = manufacturer
            }
            val model = MyMotApplication.dataManager?.getModelById(settings.getInt(MODEL_ID, 0))
            if (model != null) {
                filterConfig.selectedModel = model
            }
            filterConfig.priceFrom = settings.getInt(PRICE_FROM, 0)
            filterConfig.priceFor = settings.getInt(PRICE_FOR, 0)
            return filterConfig
        }

    fun saveCsrfToken(token: String?) {
        val oldToken = settings.getString(CSRF_TOKEN, "")
        if (oldToken != null) {
            if (oldToken.length > 0) {
                return
            }
        }
        if (token != null) {
            if (token.length > 0) {
                Log.e("saveCsrfToken", token)
                val editor = settings.edit()
                editor.putString(CSRF_TOKEN, token)
                editor.commit()
            }
        }
    }

    fun clearCsrfToken() {
        val editor = settings.edit()
        editor.putString(CSRF_TOKEN, "")
        editor.commit()
    }

    val csrfToken: String?
        get() = settings.getString(CSRF_TOKEN, "")

    companion object {
        private const val PREFS_NAME = "MyMotPreferences"
        private const val LOCATION_ID = "search_location_id"
        private const val MANUFACTURER_ID = "search_manufacturer_id"
        private const val MODEL_ID = "search_model_id"
        private const val PRICE_FROM = "search_price_from"
        private const val PRICE_FOR = "search_price_for"
        private const val CSRF_TOKEN = "autoru_csrf_token"
    }
}