package ru.michanic.mymot.Kotlin.UI.Cells

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ru.michanic.mymot.Models.YoutubeVideo
import ru.michanic.mymot.R

class ReviewsSliderCell(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var imageView: ImageView

    init {
        imageView = itemView.findViewById<View>(R.id.cell_image) as ImageView
    }

    fun fillWithVideo(video: YoutubeVideo) {
        Picasso.get().load(video.previewPath).placeholder(R.drawable.ic_placeholder).into(imageView)
    }
}