package ru.michanic.mymot.Kotlin.UI.Activities

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import ru.michanic.mymot.Kotlin.Interactors.ApiInteractor
import ru.michanic.mymot.R

class TextActivity : UniversalActivity() {
    private var loadingIndicator: ProgressBar? = null
    private var pageText: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)
        val intent = intent
        val pageTitle = intent.getStringExtra("title")
        setNavigationTitle(pageTitle)
        loadingIndicator = findViewById<View>(R.id.progressBarAgreement) as ProgressBar
        loadingIndicator?.visibility = View.VISIBLE
        pageText = findViewById<View>(R.id.pageText) as TextView
        pageText?.visibility = View.GONE
        val apiInteractor = ApiInteractor()

        apiInteractor.loadAgreementText {
            loadingIndicator?.visibility = View.GONE
            pageText?.text = Html.fromHtml(it)
            pageText?.visibility = View.VISIBLE
        }
    }
}
