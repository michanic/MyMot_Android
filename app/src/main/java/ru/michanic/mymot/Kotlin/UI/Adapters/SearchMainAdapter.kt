package ru.michanic.mymot.Kotlin.UI.Adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.michanic.mymot.Kotlin.Models.Advert
import ru.michanic.mymot.Kotlin.Protocols.ClickListener
import ru.michanic.mymot.R
import ru.michanic.mymot.Kotlin.UI.Cells.SearchMainCell

class SearchMainAdapter(
    var context: Context,
    var adverts: List<Advert>,
    var clickListener: ClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cell_advert_main, viewGroup, false)
        return SearchMainCell(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchMainCell) {
            holder.fillWithAdvert(adverts[position])
            holder.itemView.setOnClickListener { clickListener.onClick(0, position) }
        } else if (holder is HeaderViewHolder) {
            holder.setTitle("Заголовок")
        }
    }

    override fun getItemCount(): Int {
        return adverts.size
    }

    internal inner class HeaderViewHolder(var View: View) : RecyclerView.ViewHolder(
        View
    ) {
        private val headerTitle: TextView = View.findViewById<View>(R.id.headerTitle) as TextView

        fun setTitle(title: String?) {
            headerTitle.text = title
        }
    }
}