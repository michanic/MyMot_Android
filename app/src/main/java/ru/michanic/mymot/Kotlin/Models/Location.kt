package ru.michanic.mymot.Kotlin.Models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Location : RealmObject() {

    @PrimaryKey
    val id = 0
    val autoru: String? = null
    val avito: String? = null
    val name: String? = null
    private val sort = 0


    var regionId = 0
}