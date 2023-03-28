package ru.michanic.mymot.Kotlin.Models

class YoutubeVideo(var videoId: String) {

    val previewPath: String
        get() = "https://img.youtube.com/vi/$videoId/mqdefault.jpg"
    val videoPath: String
        get() = "https://www.youtube.com/watch?v=$videoId"
}