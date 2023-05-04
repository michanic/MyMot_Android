package ru.michanic.mymot.Kotlin.Models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Location : RealmObject() {

    @PrimaryKey
    var id = 0
    var autoru: String? = null
    var avito: String? = null
    var name: String? = null
    private var sort = 0


    var regionId = 0
}