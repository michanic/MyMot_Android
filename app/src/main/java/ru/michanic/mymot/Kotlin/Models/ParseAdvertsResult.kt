package ru.michanic.mymot.Kotlin.Models

class ParseAdvertsResult(val advetrs: List<Advert>, val more: Boolean) {
    fun loadMore(): Boolean {
        return more
    }
}