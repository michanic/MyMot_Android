package ru.michanic.mymot.Kotlin.Models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import ru.michanic.mymot.Kotlin.Enums.SourceType
import ru.michanic.mymot.Kotlin.Protocols.Const
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

open class Advert : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var isActive = false
    var city: String? = null
    var date: String? = null
    var isFavourite = false
    var link: String? = null
    var phone: String? = null
    var previewImage: String? = null
    var price: Int = 0
    var title: String? = null

    val priceString: String
        get() {
            val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
            val symbols = formatter.decimalFormatSymbols
            symbols.groupingSeparator = ' '
            formatter.decimalFormatSymbols = symbols
            val str = formatter.format(price.toLong())
            return str + Const.RUB
        }
    val details: String
        get() = """
               $city
               $date
               """.trimIndent()
    val sourceType: SourceType
        get() = if (link!!.contains("avito")) {
            SourceType.AVITO
        } else {
            SourceType.AUTO_RU
        }
}