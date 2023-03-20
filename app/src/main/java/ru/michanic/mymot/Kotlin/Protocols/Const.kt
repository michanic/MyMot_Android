package ru.michanic.mymot.Kotlin.Protocols

interface Const {
    companion object {
        const val DOMAIN = "http://my-mot.ru"
        const val API_URL = DOMAIN + "/api/"
        const val RUB = " р."
    }
}