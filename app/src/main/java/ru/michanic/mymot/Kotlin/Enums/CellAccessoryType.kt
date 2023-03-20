package ru.michanic.mymot.Kotlin.Enums

enum class CellAccessoryType {
    RIGHT, TOP, BOTTOM, HIDDEN, CHECKED, LOADING;

    fun angle(): Double {
        return when (this) {
            RIGHT -> 0.0
            TOP -> -Math.PI / 2
            BOTTOM -> Math.PI / 2
            HIDDEN -> 0.0
            CHECKED -> 0.0
            LOADING -> 0.0
        }
        return 0.0
    }
}