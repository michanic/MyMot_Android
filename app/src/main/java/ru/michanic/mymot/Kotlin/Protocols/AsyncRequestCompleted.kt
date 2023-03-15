package ru.michanic.mymot.Kotlin.Protocols

interface AsyncRequestCompleted {
    fun processFinish(output: Any?)
}