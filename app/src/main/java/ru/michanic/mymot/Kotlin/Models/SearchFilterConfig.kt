package ru.michanic.mymot.Kotlin.Models

class SearchFilterConfig {
    var selectedRegion: Location? = null
    var selectedManufacturer: Manufacturer? = null
    var selectedModel: Model? = null
    var priceFrom = 0
    var priceFor = 0
    val mainSearchTitle: String?
        get() {
            var sectionTitle: String? = "Все мотоциклы"
            if (selectedRegion != null) {
                sectionTitle = selectedRegion!!.name
            }
            if (priceFrom > 0) {
                sectionTitle += if (priceFor > 0) {
                    ",\n$priceFrom - $priceFor руб."
                } else {
                    ",\nЦена от $priceFrom руб."
                }
            } else if (priceFor > 0) {
                sectionTitle += ",\nЦена до $priceFor руб."
            }
            return sectionTitle
        }
}