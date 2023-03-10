package ru.michanic.mymot.Kotlin.Models

import com.google.gson.internal.LinkedTreeMap
import ru.michanic.mymot.Protocols.Const

class ModelDetails {
    val preview_text: String? = null
    val images: List<String>? = null
        get() {
            val fullImages: MutableList<String> = ArrayList()
            if (field != null) {
                for (path in field) {
                    fullImages.add(Const.DOMAIN + path)
                }
            }
            return fullImages
        }
    val video_reviews: List<String>? = null
    val parameters: List<LinkedTreeMap<String, String>>? = null
}