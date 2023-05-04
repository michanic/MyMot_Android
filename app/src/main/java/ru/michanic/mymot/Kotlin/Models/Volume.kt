package ru.michanic.mymot.Kotlin.Models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import ru.michanic.mymot.Kotlin.Models.Model

open class Volume : RealmObject() {
    @PrimaryKey
    var id = 0
    var code: String? = null
    var image: String? = null
    var name: String? = null
    private var sort = 0
    var min = 0
    var max = 0

    var models: RealmList<Model>? = null
}