package ru.michanic.mymot.Kotlin.Models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Model : RealmObject() {
    @PrimaryKey
    var id = 0
    private var code: String? = null
    var class_id = 0
    var m_id = 0
    var isFavourite = false
    private var first_year = 0
    private var last_year = 0
    var name: String? = null
    var preview_picture: String? = null
    var sort = 0
    var volume: String? = null
    private var volume_value = 0f
    var volume_id = 0

    var cylyndersCount: Int = 0
    var cylyndersPlacement: Int = 0
    var cooling: Int = 0
    var power: String? = null
    var driveType: Int = 0
    var seatHeight: String? = null
    var wetWeight: String? = null


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