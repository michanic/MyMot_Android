package ru.michanic.mymot.Kotlin.Models

import com.google.gson.internal.LinkedTreeMap

class AdvertDetails {
    var images: List<String>? = null
    var text: String? = null
    var parameters: List<LinkedTreeMap<String, String>>? = null
    var date: String? = null
    var saleHash: String? = null
    var warning = ""
    var csrfToken: String? = null
}