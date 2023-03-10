package ru.michanic.mymot.Kotlin.Models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Manufacturer : RealmObject() {
    @PrimaryKey
    val id = 0
    val code: String? = null
    val image: String? = null
    val name: String? = null
    private val sort = 0

    val models: RealmList<Model>? = null
    val avitoSearchName: String
        get() = name!!.replace(" ", "%20").lowercase(Locale.getDefault())


    val autoruSearchName: String
        get() = "$code/"
}