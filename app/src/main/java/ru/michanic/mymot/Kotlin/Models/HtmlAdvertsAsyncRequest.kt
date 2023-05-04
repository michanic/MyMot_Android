package ru.michanic.mymot.Kotlin.Models

import android.os.AsyncTask
import org.jsoup.nodes.Document
import ru.michanic.mymot.Kotlin.Enums.SourceType
import ru.michanic.mymot.Kotlin.MyMotApplication
import ru.michanic.mymot.Kotlin.Protocols.AsyncRequestCompleted
import java.io.IOException

class HtmlAdvertsAsyncRequest(asyncResponse: AsyncRequestCompleted?, sourceType: SourceType) :
    AsyncTask<String?, Void?, ParseAdvertsResult>() {
    var delegate: AsyncRequestCompleted? = null
    private val htmlParser = HtmlParser()
    private val sourceType: SourceType

    init {
        delegate = asyncResponse
        this.sourceType = sourceType
    }

    override fun doInBackground(vararg arg: String?): ParseAdvertsResult {
        val path = arg[0]
        val response =
            MyMotApplication.networkService?.getHtmlData(path)
        var doc: Document? = null
        try {
            doc = response?.parse()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return htmlParser.parseAdverts(doc, sourceType)
    }

    override fun onPostExecute(result: ParseAdvertsResult) {
        delegate?.processFinish(result)
    }
}