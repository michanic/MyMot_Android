package ru.michanic.mymot.Kotlin

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import ru.michanic.mymot.Kotlin.Utils.ConfigStorage
import ru.michanic.mymot.Kotlin.Utils.DataManager
import ru.michanic.mymot.Kotlin.Utils.NetworkService
import ru.michanic.mymot.Kotlin.Utils.SearchManager

class MyMotApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        @JvmField
        @Volatile
        var appContext: Context? = null
        var searchManager: SearchManager? = null
        @JvmField
        var dataManager: DataManager? = null
        @JvmField
        var configStorage: ConfigStorage? = null
        var networkService: NetworkService? = null
        fun setAppContext(appContext: Context?) {
            Companion.appContext = appContext
            dataManager = DataManager()
            configStorage = appContext?.let { ConfigStorage(it) }
            searchManager = SearchManager()
            networkService = NetworkService()
            AppCompatDelegate.setDefaultNightMode(configStorage?.colorMode ?: 0)
        }
    }
}