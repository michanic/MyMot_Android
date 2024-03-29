package ru.michanic.mymot.Kotlin.UI.Adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.michanic.mymot.Kotlin.Models.Advert
import ru.michanic.mymot.Kotlin.Protocols.ClickListener
import ru.michanic.mymot.R
import ru.michanic.mymot.Kotlin.UI.Cells.SearchListCell

class AdvertsListAdapter(
    var context: Context,
    var adverts: List<Advert>,
    var clickListener: ClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cell_adverts_list, viewGroup, false)
        return SearchListCell(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchListCell) {
            holder.fillWithAdvert(adverts[position])
            holder.itemView.setOnClickListener { clickListener.onClick(0, position) }
        }
    }

    override fun getItemCount(): Int {
        return adverts.size
    }
}