package ru.michanic.mymot.Kotlin.UI.Activities

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableString
import ru.michanic.mymot.Kotlin.Protocols.NoConnectionRepeatInterface
import ru.michanic.mymot.Kotlin.Utils.TypefaceSpan

open class UniversalActivity : AppCompatActivity() {
    var rootActivity = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!rootActivity) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun setNavigationTitle(title: String) {
        val s = SpannableString(" $title ")
        s.setSpan(
            TypefaceSpan(this, "Progress.ttf"),
            0,
            s.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        supportActionBar?.title = s
    }

    fun showNoConnectionDialog(noConnectionRepeatInterface: () -> Unit) {
        showDialog(
            "Ошибка",
            "Отсутствует соединение с сервером",
            noConnectionRepeatInterface
        )
    }

    protected fun showDialog(
        title: String?,
        message: String?,
        noConnectionRepeatInterface: NoConnectionRepeatInterface,
    ) {
        val alertDialogBuilder = AlertDialog.Builder(this@UniversalActivity)
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("Повторить") { dialogInterface, i ->
            noConnectionRepeatInterface.repeatPressed()
        }
        val alertDialog = alertDialogBuilder.create() as AlertDialog
        alertDialog.show()
    }
}