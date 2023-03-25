package ru.michanic.mymot.Kotlin.UI.Adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import de.halfbit.pinnedsection.PinnedSectionListView.PinnedSectionListAdapter
import ru.michanic.mymot.Kotlin.Extensions.Font
import ru.michanic.mymot.Kotlin.Models.SectionModelItem
import ru.michanic.mymot.Kotlin.MyMotApplication
import ru.michanic.mymot.Kotlin.Protocols.Const
import ru.michanic.mymot.R

class SectionItemsListAdapter : BaseAdapter, PinnedSectionListAdapter {
   private var items: List<SectionModelItem>? = null

    constructor(nullableItems: List<SectionModelItem?>) {
        this.items = nullableItems.filterNotNull()
    }

    fun setItems(items: List<SectionModelItem?>) {
        this.items = items.filterNotNull()
    }


    override fun isItemViewTypePinned(viewType: Int): Boolean {
        return viewType == SectionModelItem.SECTION_TITLE
    }

    override fun getCount(): Int {
        return items!!.size  //TODO: ?
    }

    override fun getItem(position: Int): Any {
        return items!![position] //TODO: ?
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getViewTypeCount(): Int {
        return 4
    }

    override fun getItemViewType(position: Int): Int {
        return items!![position].type //TODO: ?
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val item = items!![position] //TODO: ?
        var view = convertView
        when (item.type) {
            SectionModelItem.SECTION_TITLE -> {
                view = View.inflate(parent.context, R.layout.cell_section_title, null)
                val title = view.findViewById<View>(R.id.section_title) as TextView
                title.text = item.sectionTitle
                title.typeface = Font.oswald
            }
            SectionModelItem.LIST_MODEL -> {
                view = View.inflate(parent.context, R.layout.cell_models_list, null)
                val imageView = view.findViewById<View>(R.id.cell_image) as ImageView
                val modelTitle = view.findViewById<View>(R.id.model_title) as TextView
                val years = view.findViewById<View>(R.id.model_years) as TextView
                val volume = view.findViewById<View>(R.id.model_volume) as TextView
                val model = item.model
                Picasso.get().load(Const.DOMAIN + model?.preview_picture)
                    .placeholder(R.drawable.ic_placeholder).into(imageView)
                modelTitle.text = model?.name
                years.text = model?.years
                volume.text = model?.volume
            }
            SectionModelItem.SIMPLE_CELL -> {
                view = View.inflate(parent.context, R.layout.cell_simple, null)
                val simpleTitle = view.findViewById<View>(R.id.textView) as TextView
                simpleTitle.text = item.propertyTitle
            }
            SectionModelItem.PRICE_CELL -> {
                view = View.inflate(parent.context, R.layout.cell_price, null)
                val priceTitle = view.findViewById<View>(R.id.textView) as TextView
                val priceValue = view.findViewById<View>(R.id.priceValue) as EditText
                val propertyTitle = item.propertyTitle
                priceValue.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int,
                    ) {
                        if (propertyTitle === SectionModelItem.PRICE_FROM_NAME) {
                            try {
                                val priceFrom = s.toString().toInt()
                                MyMotApplication.searchManager?.priceFrom = priceFrom
                            } catch (nfe: NumberFormatException) {
                                MyMotApplication.searchManager?.priceFrom = 0
                            }
                        } else if (propertyTitle === SectionModelItem.PRICE_FOR_NAME) {
                            try {
                                val priceFor = s.toString().toInt()
                                MyMotApplication.searchManager?.priceFor = priceFor
                            } catch (nfe: NumberFormatException) {
                                MyMotApplication.searchManager?.priceFor = 0
                            }
                        }
                        item.propertyValue = s.toString()
                    }

                    override fun afterTextChanged(s: Editable) {}
                })
                priceTitle.text = propertyTitle
                priceValue.setText(item.propertyValue)
            }
        }
        return view
    }

}