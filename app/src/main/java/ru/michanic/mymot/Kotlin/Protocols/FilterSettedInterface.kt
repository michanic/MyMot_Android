package ru.michanic.mymot.Kotlin.Protocols

import ru.michanic.mymot.Kotlin.Models.SearchFilterConfig

interface FilterSettedInterface {
    fun onSelected(filterConfig: SearchFilterConfig?)
}