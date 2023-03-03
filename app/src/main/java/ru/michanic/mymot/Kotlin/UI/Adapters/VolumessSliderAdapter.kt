package ru.michanic.mymot.Kotlin.UI.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.michanic.mymot.Models.Volume
import ru.michanic.mymot.Protocols.ClickListener
import ru.michanic.mymot.R
import ru.michanic.mymot.Kotlin.UI.Cells.CatalogSliderCell

class VolumessSliderAdapter(
    var context: Context,
    var volumes: List<Volume>,
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
            holder.fillWithVolume(volumes[position])
            holder.itemView.setOnClickListener { clickListener.onClick(0, position) }
        }
    }

    override fun getItemCount(): Int {
        return volumes.size
    }
}