package ru.michanic.mymot.Kotlin.Models

import android.os.Build
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import ru.michanic.mymot.Kotlin.Utils.ModelsSortComparator
import java.util.*

open class Category : RealmObject() {
    @PrimaryKey
    val id = 0
    private val description: String? = null
    private val code: String? = null
    val image: String? = null
    val name: String? = null
    private val sort = 0

    var models: RealmList<Model>? = null
    fun getManufacturerModels(manufacturerId: Int): List<Model> {
        val modelsList: MutableList<Model> = ArrayList()
        for (model in models!!) {
            if (model.manufacturer?.id == manufacturerId) {
                modelsList.add(model)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(modelsList, ModelsSortComparator())
        }
        return modelsList
    }
}