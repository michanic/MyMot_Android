package ru.michanic.mymot.Kotlin.Utils

import android.util.Log
import ru.michanic.mymot.Kotlin.Models.Location
import ru.michanic.mymot.Kotlin.Models.Manufacturer
import ru.michanic.mymot.Kotlin.Models.Model
import ru.michanic.mymot.Kotlin.MyMotApplication
import ru.michanic.mymot.Kotlin.Protocols.FilterSettedInterface

class SearchManager {
    private var filterChanged = false
    val filterConfig = MyMotApplication.configStorage!!.filterConfig
    var filterUpdated: FilterSettedInterface? = null
    var searchPressedCallback: FilterSettedInterface? = null
    var filterClosedCallback: FilterSettedInterface? = null
    var region: Location?
        get() = filterConfig.selectedRegion
        set(region) {
            filterConfig.selectedRegion = region
            filterChanged = true
            if (filterUpdated != null) {
                filterUpdated!!.onSelected(filterConfig)
            }
            MyMotApplication.configStorage!!.saveFilterConfig(filterConfig)
        }
    val regionTitle: String?
        get() {
            val region = filterConfig.selectedRegion
            return if (region != null) {
                region.name
            } else {
                "По всей России"
            }
        }
    var manufacturer: Manufacturer?
        get() = filterConfig.selectedManufacturer
        set(manufacturer) {
            filterConfig.selectedManufacturer = manufacturer
            filterConfig.selectedModel = null
            filterChanged = true
            if (filterUpdated != null) {
                filterUpdated!!.onSelected(filterConfig)
            }
            MyMotApplication.configStorage!!.saveFilterConfig(filterConfig)
        }
    var model: Model?
        get() = filterConfig.selectedModel
        set(model) {
            filterConfig.selectedManufacturer = null
            filterConfig.selectedModel = model
            filterChanged = true
            if (filterUpdated != null) {
                filterUpdated!!.onSelected(filterConfig)
            }
            MyMotApplication.configStorage!!.saveFilterConfig(filterConfig)
        }
    val modelTitle: String?
        get() {
            val manufacturer = filterConfig.selectedManufacturer
            val model = filterConfig.selectedModel
            return if (manufacturer != null) {
                "Все мотоциклы " + manufacturer.name
            } else if (model != null) {
                model.name
            } else {
                "Все мотоциклы"
            }
        }

    //filterUpdated.onSelected(filterConfig);
    var priceFrom: Int
        get() = filterConfig.priceFrom
        set(newPrice) {
            filterConfig.priceFrom = newPrice
            filterChanged = true
            //filterUpdated.onSelected(filterConfig);
            MyMotApplication.configStorage!!.saveFilterConfig(filterConfig)
        }

    //filterUpdated.onSelected(filterConfig);
    var priceFor: Int
        get() = filterConfig.priceFor
        set(newPrice) {
            filterConfig.priceFor = newPrice
            filterChanged = true
            //filterUpdated.onSelected(filterConfig);
            MyMotApplication.configStorage!!.saveFilterConfig(filterConfig)
        }

    fun backPressed() {
        Log.e("backPressed", filterChanged.toString())
        if (filterClosedCallback != null && filterChanged) {
            filterClosedCallback!!.onSelected(filterConfig)
            filterChanged = false
        }
    }

    fun searchPressed() {
        searchPressedCallback!!.onSelected(filterConfig)
    }
}