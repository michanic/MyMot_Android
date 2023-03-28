package ru.michanic.mymot.Kotlin.Protocols

import ru.michanic.mymot.Kotlin.Models.AdvertDetails

interface LoadingAdvertDetailsInterface {
    fun onLoaded(advertDetails: AdvertDetails?)
    fun onFailed()
}