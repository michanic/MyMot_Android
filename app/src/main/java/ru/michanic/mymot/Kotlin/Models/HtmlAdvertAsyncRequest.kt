package ru.michanic.mymot.Kotlin.Models

import android.os.AsyncTask
import org.jsoup.nodes.Document
import ru.michanic.mymot.Enums.SourceType
import ru.michanic.mymot.MyMotApplication
import ru.michanic.mymot.Protocols.AsyncRequestCompleted
import java.io.IOException

abstract class HtmlAdvertAsyncRequest(asyncResponse: AsyncRequestCompleted?, sourceType: SourceType) :
    AsyncTask<String?, Void?, AdvertDetails>() {
    var delegate: AsyncRequestCompleted? = null
    private val htmlParser = HtmlParser()
    private val sourceType: SourceType

    init {
        delegate = asyncResponse
        this.sourceType = sourceType
    }

    override fun doInBackground(vararg arg: String?): AdvertDetails? {
        val path = arg[0]
        val response = MyMotApplication.networkService.getHtmlData(path)
        var doc: Document? = null
        val csrf_token = response.cookie("_csrf_token")

        try {
            doc = response.parse()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val advertDetails = htmlParser.parseAdvertDetails(doc, sourceType)
        advertDetails.csrfToken = csrf_token
        return advertDetails
    }

    override fun onPostExecute(result: AdvertDetails) {

        delegate!!.processFinish(result)
    }
}