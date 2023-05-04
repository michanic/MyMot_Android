package ru.michanic.mymot.Kotlin.Enums

enum class SourceType {
    AVITO, AUTO_RU;

    fun domain(): String? {
        return when (this) {
            AVITO -> "https://www.avito.ru/"
            AUTO_RU -> "https://auto.ru/"
        }
        return null
    }

    fun mobileDomain(): String? {
        return when (this) {
            AVITO -> "https://m.avito.ru/"
            AUTO_RU -> "https://m.auto.ru/"
        }
        return null
    }

    fun itemSelector(): String? {
        return when (this) {
            AVITO -> ".js-catalog-item-enum.item-with-contact"
            AUTO_RU -> ".listing-item.stat-publicapi_type_listing"
        }
        return null
    }
}