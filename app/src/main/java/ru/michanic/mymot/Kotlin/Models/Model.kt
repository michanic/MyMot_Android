package ru.michanic.mymot.Kotlin.Models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Model : RealmObject() {
    @PrimaryKey
    val id = 0
    private val code: String? = null
    val class_id = 0
    val m_id = 0
    var isFavourite = false
    private val first_year = 0
    private val last_year = 0
    val name: String? = null
    val preview_picture: String? = null
    val sort = 0
    var volume: String? = null
    private var volume_value = 0f
    var volume_id = 0

    var category: Category? = null
    var manufacturer: Manufacturer? = null
    fun setVolume_value(volume_value: Float) {
        this.volume_value = volume_value
    }

    val years: String
        get() = first_year.toString() + " - " + if (last_year == 0) "настоящее время" else last_year
    val avitoSearchName: String
        get() = manufacturer?.name?.lowercase(Locale.getDefault()) + "+" + name?.replace(
            " ",
            "+"
        )?.lowercase(
            Locale.getDefault()
        )

    val autoruSearchName: String
        get() = manufacturer?.code + "/" + code + "/"
}