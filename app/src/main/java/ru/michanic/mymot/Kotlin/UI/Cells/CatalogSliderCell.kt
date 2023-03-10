package ru.michanic.mymot.Kotlin.UI.Cells

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import ru.michanic.mymot.Kotlin.Models.Category
import ru.michanic.mymot.Kotlin.Models.Manufacturer
import ru.michanic.mymot.Kotlin.Models.Volume
import ru.michanic.mymot.Protocols.Const
import ru.michanic.mymot.R

class CatalogSliderCell(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var imageView: ImageView
    var titleLabel: TextView

    init {
        imageView = itemView.findViewById<View>(R.id.cell_image) as ImageView
        titleLabel = itemView.findViewById<View>(R.id.cell_title) as TextView
    }

    fun fillWithCategory(category: Category) {
        Picasso.get().load(Const.DOMAIN + category.image).placeholder(R.drawable.ic_placeholder)
            .into(imageView)
        titleLabel.text = category.name
    }

    fun fillWithManufacturer(manufacturer: Manufacturer) {
        Picasso.get().load(Const.DOMAIN + manufacturer.image).placeholder(R.drawable.ic_placeholder)
            .into(imageView)
        titleLabel.text = manufacturer.name
    }

    fun fillWithVolume(volume: Volume) {
        Picasso.get().load(Const.DOMAIN + volume.image).placeholder(R.drawable.ic_placeholder)
            .into(imageView)
        titleLabel.text = volume.name
    }
}