package ru.michanic.mymot.Kotlin.Models

class SectionModelItem {
    var sectionTitle: String? = null
    var model: Model? = null
    var propertyTitle: String? = null
    var propertyValue: String? = null
    val type: Int

    constructor(sectionTitle: String?) {
        type = SECTION_TITLE
        this.sectionTitle = sectionTitle
    }

    constructor(model: Model?) {
        type = LIST_MODEL
        this.model = model
    }

    constructor(propertyTitle: String?, propertyValue: String?) {
        this.propertyTitle = propertyTitle
        if (propertyValue != null) {
            type = PRICE_CELL
            this.propertyValue = propertyValue
        } else {
            type = SIMPLE_CELL
        }
    }

    companion object {
        const val SECTION_TITLE = 0
        const val LIST_MODEL = 1
        const val SIMPLE_CELL = 2
        const val PRICE_CELL = 3
        const val PRICE_FROM_NAME = "От"
        const val PRICE_FOR_NAME = "До"
    }
}