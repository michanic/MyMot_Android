package ru.michanic.mymot.Kotlin.UI.Cells

import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import ru.michanic.mymot.Kotlin.Models.Advert
import ru.michanic.mymot.Kotlin.MyMotApplication
import ru.michanic.mymot.R

class SearchListCell(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var imageView: ImageView
    var titleLabel: TextView
    var priceText: TextView
    var detailsText: TextView
    var favouriteIcon: ImageView

    init {
        imageView = itemView.findViewById<View>(R.id.cell_image) as ImageView
        titleLabel = itemView.findViewById<View>(R.id.advert_title) as TextView
        priceText = itemView.findViewById<View>(R.id.price_label) as TextView
        detailsText = itemView.findViewById<View>(R.id.advert_details) as TextView
        favouriteIcon = itemView.findViewById<View>(R.id.favourite_icon) as ImageView
    }

    fun fillWithAdvert(advert: Advert) {
        Picasso.get().load(advert.previewImage).placeholder(R.drawable.ic_placeholder)
            .into(imageView)
        titleLabel.text = advert.title
        priceText.text = advert.priceString
        detailsText.text = advert.details
        imageView.setAlpha(if (advert.isActive) 1 else 0.5.toInt())
        priceText.setAlpha((if (advert.isActive) 1 else 0.5.toFloat()) as Float)
        switchFavouriteButton(advert.isFavourite)
        favouriteIcon.setOnClickListener {
            val newState = !advert.isFavourite
            Log.e("setAdvertFavourite", newState.toString())
            MyMotApplication.dataManager?.setAdvertFavourite(advert.id, newState)
            switchFavouriteButton(newState)
        }
    }

    private fun switchFavouriteButton(favourite: Boolean) {
        if (favourite) {
            favouriteIcon.setImageResource(R.drawable.ic_list_favourite_active)
        } else {
            favouriteIcon.setImageResource(R.drawable.ic_list_favourite_passive)
        }
    }
}

