package ru.michanic.mymot.Kotlin.Models

import android.os.AsyncTask
import android.util.Log
import org.jsoup.nodes.Document
import ru.michanic.mymot.Enums.SourceType
import ru.michanic.mymot.MyMotApplication
import ru.michanic.mymot.Protocols.AsyncRequestCompleted
import java.io.IOException

class HtmlAdvertPhoneAsyncRequest(asyncResponse: AsyncRequestCompleted?, sourceType: SourceType) :
    AsyncTask<String?, Void?, List<String?>>() {
    var delegate: AsyncRequestCompleted? = null
    private val htmlParser = HtmlParser()
    private val sourceType: SourceType

    init {
        delegate = asyncResponse
        this.sourceType = sourceType
    }

    override fun doInBackground(vararg arg: String?): List<String?>? {
        val path = arg[0]
        Log.e("doInBackground", path)
        var doc: Document
        var phones: MutableList<String?> = ArrayList()
        val csrfToken = MyMotApplication.configStorage.csrfToken
        Log.e("csrfToken", csrfToken)
        if (sourceType == SourceType.AVITO) {
            val response = MyMotApplication.networkService.getHtmlDataAsMobile(path)
            try {
                doc = response.parse()
            } catch (e: IOException) {
                e.printStackTrace()
                return phones
            }
            phones.add(htmlParser.parsePhoneFromAvito(doc))
        } else if (sourceType == SourceType.AUTO_RU) {
            doc = MyMotApplication.networkService.getAutoRuPhonesData(path, csrfToken)
            if (doc != null) {
                try {
                    phones = htmlParser.parsePhonesFromAutoRu(doc).toMutableList()
                } catch (t: Throwable) {
                    Log.e("My App", "Could not parse malformed JSON")
                }
            } else {
                Log.e("doc", "is empty")
            }
        }
        return phones
    }

    override fun onPostExecute(phones: List<String?>) {
        delegate?.processFinish(phones)
    }
}