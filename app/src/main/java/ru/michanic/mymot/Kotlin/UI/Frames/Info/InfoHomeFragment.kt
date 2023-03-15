package ru.michanic.mymot.Kotlin.UI.Frames.Info

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatDelegate
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import ru.michanic.mymot.Kotlin.UI.Activities.TextActivity
import ru.michanic.mymot.Kotlin.MyMotApplication
import ru.michanic.mymot.R

class InfoHomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_info_home, null)
        val aboutText = rootView.findViewById<View>(R.id.aboutText) as TextView
        val htmlText = Html.fromHtml(MyMotApplication.configStorage.aboutText)
        aboutText.text = htmlText
        val agreementView = rootView.findViewById<View>(R.id.agreementView) as FrameLayout
        agreementView.setOnClickListener {
            val textActivity = Intent(activity, TextActivity::class.java)
            textActivity.putExtra("title", "Пользовательское соглашение")
            activity?.startActivity(textActivity)
        }
        return rootView
    }

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}