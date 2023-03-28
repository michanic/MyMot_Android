package ru.michanic.mymot.Kotlin.Models

class FilterModelItem {
    var title: String? = null
    var category: Category? = null
    var isChecked = false
    var models: List<Model>? = null
    var manufacturer: Manufacturer? = null
    val type: Int

    constructor(sectionTitle: String?) {
        type = SECTION_TITLE
        title = sectionTitle
        isChecked = isChecked
    }

    constructor(checked: Boolean) {
        type = SIMPLE_CELL
        title = "Все мотоциклы"
        isChecked = checked
    }

    constructor(manufacturer: Manufacturer, checked: Boolean) {
        type = SIMPLE_CELL
        title = "Все мотоциклы " + manufacturer.name
        this.manufacturer = manufacturer
        isChecked = checked
    }

    constructor(category: Category, models: List<Model>?) {
        type = CATEGORY_CELL
        title = category.name
        this.category = category
        this.models = models
    }

    companion object {
        const val SECTION_TITLE = 0
        const val SIMPLE_CELL = 1
        const val CATEGORY_CELL = 2
    }
}