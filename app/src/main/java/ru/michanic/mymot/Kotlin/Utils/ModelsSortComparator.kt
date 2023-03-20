package ru.michanic.mymot.Kotlin.Utils

import ru.michanic.mymot.Kotlin.Models.Model

class ModelsSortComparator : Comparator<Model> {
    override fun compare(m1: Model, m2: Model): Int {
        return m1.sort - m2.sort
    }
}