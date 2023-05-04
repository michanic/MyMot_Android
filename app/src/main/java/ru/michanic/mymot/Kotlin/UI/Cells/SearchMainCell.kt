package ru.michanic.mymot.Kotlin.UI.Cells

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import ru.michanic.mymot.Kotlin.Models.Advert
import ru.michanic.mymot.R

class SearchMainCell(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var imageView: ImageView
    var titleLabel: TextView
    var priceText: TextView
    var detailsText: TextView

    init {
        imageView = itemView.findViewById<View>(R.id.cell_image) as ImageView
        titleLabel = itemView.findViewById<View>(R.id.cell_title) as TextView
        priceText = itemView.findViewById<View>(R.id.cell_price) as TextView
        detailsText = itemView.findViewById<View>(R.id.cell_details) as TextView
    }

    fun fillWithAdvert(advert: Advert) {
        Picasso.get().load(advert.previewImage).placeholder(R.drawable.ic_placeholder)
            .into(imageView)
        titleLabel.text = advert.title
        priceText.text = advert.priceString
        detailsText.text = advert.details
    }
}