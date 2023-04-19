package ru.michanic.mymot.Kotlin.UI.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import ru.michanic.mymot.Kotlin.Extensions.Font
import ru.michanic.mymot.Kotlin.Interactors.ApiInteractor
import ru.michanic.mymot.Kotlin.MyMotApplication
import ru.michanic.mymot.R
import ru.michanic.mymot.databinding.ActivityLoadingBinding

class LoadingActivity : UniversalActivity() {

    private lateinit var binding: ActivityLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        supportActionBar?.hide()
        MyMotApplication.setAppContext(applicationContext)
        val bigTitle = findViewById<View>(R.id.bigTitle) as TextView
        bigTitle.typeface = Font.progress
        bigTitle.text = "Загрузка"
        val subtitle = findViewById<View>(R.id.subtitle) as TextView
        subtitle.text = "Синхронизация каталога"
        loadData()
    }

    private fun loadData() {
        val apiInteractor = ApiInteractor()
        apiInteractor.loadData({
            val mainActivity = Intent(MyMotApplication.appContext, MainActivity::class.java)
            mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(mainActivity)
        }, {
            showNoConnectionDialog { loadData() }
        })

    }
}