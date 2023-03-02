package ru.michanic.mymot.Kotlin.UI.Adapters

import android.content.Context
import android.widget.ExpandableListView

class ExpandableListAdapter(context: Context?) : ExpandableListView(context) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec
        val lDisplayMetrics = resources.displayMetrics
        widthMeasureSpec =
            MeasureSpec.makeMeasureSpec(lDisplayMetrics.widthPixels, MeasureSpec.AT_MOST)
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(1000000, MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}