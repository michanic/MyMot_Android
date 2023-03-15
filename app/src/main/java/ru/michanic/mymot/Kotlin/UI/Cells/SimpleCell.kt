package ru.michanic.mymot.Kotlin.UI.Cells

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import ru.michanic.mymot.Kotlin.Enums.CellAccessoryType
import ru.michanic.mymot.R

object SimpleCell {
    fun fillWithTitle(view: View, title: String, accessoryType: CellAccessoryType?, level: Int) {
        var title = title
        val arrowImage = view.findViewById<View>(R.id.arrowView) as ImageView
        val simpleLabel = view.findViewById<View>(R.id.textView) as TextView
        val loadingIndicator = view.findViewById<View>(R.id.progressBar) as ProgressBar
        if (level == 2) {
            title = "    $title"
        }
        simpleLabel.text = title
        loadingIndicator.visibility = View.GONE
        when (accessoryType) {
            CellAccessoryType.RIGHT -> arrowImage.setImageResource(R.drawable.ic_arrow_right)
            CellAccessoryType.TOP -> arrowImage.setImageResource(R.drawable.ic_arrow_up)
            CellAccessoryType.BOTTOM -> arrowImage.setImageResource(R.drawable.ic_arrow_down)
            CellAccessoryType.HIDDEN -> arrowImage.visibility = View.GONE
            CellAccessoryType.CHECKED -> arrowImage.setImageResource(R.drawable.ic_checked)
            CellAccessoryType.LOADING -> {
                arrowImage.visibility = View.GONE
                loadingIndicator.visibility = View.VISIBLE
            }
        }
    }
}