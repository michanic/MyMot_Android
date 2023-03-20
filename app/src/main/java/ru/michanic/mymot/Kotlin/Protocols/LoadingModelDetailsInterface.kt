package ru.michanic.mymot.Kotlin.Protocols

import ru.michanic.mymot.Kotlin.Models.ModelDetails

interface LoadingModelDetailsInterface {
    fun onLoaded(modelDetails: ModelDetails?)
    fun onFailed()
}