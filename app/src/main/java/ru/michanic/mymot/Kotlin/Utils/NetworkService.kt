package ru.michanic.mymot.Kotlin.Utils

import android.util.Log
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class NetworkService {
    var cookies: Map<String, String> = HashMap()
    fun getHtmlData(path: String?): Connection.Response? {
        return try {
            val response = Jsoup
                .connect(path)
                .cookies(cookies)
                .execute()
            cookies = response.cookies()
            response
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun getHtmlDataAsMobile(path: String?): Connection.Response? {
        return try {
            val response = Jsoup
                .connect(path)
                .userAgent("Mozilla/5.0 (Linux; Android 7.0; SM-G930V Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.125 Mobile Safari/537.36")
                .cookies(cookies)
                .execute()
            cookies = response.cookies()
            response
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun getAutoRuPhonesData(path: String?, csrfToken: String): Document? {
        val headers: HashMap<String?, String?> = object : HashMap<String?, String?>() {
            init {
                put("x-csrf-token", csrfToken)
                put("Cookie", "_csrf_token=$csrfToken")
            }
        }
        var cardPhonesDoc: Document? = null
        try {
            val response = Jsoup.connect(path).headers(headers).ignoreContentType(true).execute()
            val json = response.body()
            Log.e("json", json)
            val cardPhonesHtml =
                json.replace("{\"blocks\":{\"card-phones\":\"", "").replace("}}", "")
                    .replace("\\\"", "")
            cardPhonesDoc = Jsoup.parse(cardPhonesHtml)
        } catch (e: IOException) {
            Log.e("error", e.localizedMessage)
            e.printStackTrace()
        }
        return cardPhonesDoc
    }
}