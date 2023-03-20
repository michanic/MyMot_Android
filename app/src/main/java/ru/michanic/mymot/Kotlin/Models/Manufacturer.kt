package ru.michanic.mymot.Kotlin.Models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Manufacturer : RealmObject() {
    @PrimaryKey
    var id = 0
    var code: String? = null
    var image: String? = null
    var name: String? = null
    private var sort = 0

    var models: RealmList<Model>? = null
    val avitoSearchName: String
        get() = name!!.replace(" ", "%20").lowercase(Locale.getDefault())


    val autoruSearchName: String
        get() = "$code/"
}