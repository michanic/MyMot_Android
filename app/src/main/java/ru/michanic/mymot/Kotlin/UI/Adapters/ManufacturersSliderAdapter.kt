package ru.michanic.mymot.Kotlin.UI.Adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.michanic.mymot.Kotlin.Models.Manufacturer
import ru.michanic.mymot.Kotlin.Protocols.ClickListener
import ru.michanic.mymot.R
import ru.michanic.mymot.Kotlin.UI.Cells.CatalogSliderCell

class ManufacturersSliderAdapter(
    var context: Context,
    var manufacturers: List<Manufacturer>,
    var clickListener: ClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        if (position == 0) {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.cell_catalog_slide, viewGroup, false)
            viewHolder = CatalogSliderCell(view)
        }
        return viewHolder ?: throw NullPointerException("Expression 'viewHolder' must not be null")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CatalogSliderCell) {
            holder.fillWithManufacturer(manufacturers[position])
            holder.itemView.setOnClickListener { clickListener.onClick(0, position) }
        }
    }

    override fun getItemCount(): Int {
        return manufacturers.size
    }
}