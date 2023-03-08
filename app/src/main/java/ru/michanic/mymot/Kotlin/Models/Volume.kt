package ru.michanic.mymot.Kotlin.Models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import ru.michanic.mymot.Kotlin.Models.Model

open class Volume : RealmObject() {
    @PrimaryKey
    val id = 0
    val code: String? = null
    val image: String? = null
    val name: String? = null
    private val sort = 0
    val min = 0
    val max = 0

    val models: RealmList<Model>? = null
}