package ru.michanic.mymot.Kotlin.UI.Adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import ru.michanic.mymot.Enums.CellAccessoryType
import ru.michanic.mymot.Kotlin.Models.FilterModelItem
import ru.michanic.mymot.MyMotApplication
import ru.michanic.mymot.Protocols.Const
import ru.michanic.mymot.R
import ru.michanic.mymot.Kotlin.UI.Cells.SimpleCell

class ModelsExpandableListAdapter(
    var context: Context,
    private val topCells: List<FilterModelItem>
) : BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return topCells.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        val item = topCells[groupPosition]
        when (item.type) {
            FilterModelItem.SECTION_TITLE -> return 0
            FilterModelItem.SIMPLE_CELL -> return 0
            FilterModelItem.CATEGORY_CELL -> return item.models?.size ?: 0
        }
        return 0
    }

    override fun getGroup(groupPosition: Int): Any {
        return groupPosition
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return childPosition
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View,
        parent: ViewGroup
    ): View? {
        val item = topCells[groupPosition]
        when (item.type) {
            FilterModelItem.SECTION_TITLE -> {
                val sectionView = View.inflate(context, R.layout.cell_section_title, null)
                val title = sectionView.findViewById<View>(R.id.section_title) as TextView
                title.text = item.title
                return sectionView
            }
            FilterModelItem.SIMPLE_CELL -> {
                val view = View.inflate(context, R.layout.cell_simple, null)
                SimpleCell.fillWithTitle(
                    view,
                    item.title  ?: "",
                    if (item.isChecked) CellAccessoryType.CHECKED else CellAccessoryType.HIDDEN,
                    1
                )
                return view
            }
            FilterModelItem.CATEGORY_CELL -> {
                val categoryView = View.inflate(context, R.layout.cell_simple, null)
                SimpleCell.fillWithTitle(
                    categoryView,
                    item.title  ?: "",
                    if (isExpanded) CellAccessoryType.TOP else CellAccessoryType.BOTTOM,
                    1
                )
                return categoryView
            }
        }
        return null
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View,
        parent: ViewGroup
    ): View {
        val view = View.inflate(context, R.layout.cell_models_list, null)
        val imageView = view.findViewById<View>(R.id.cell_image) as ImageView
        val modelTitle = view.findViewById<View>(R.id.model_title) as TextView
        val years = view.findViewById<View>(R.id.model_years) as TextView
        val arrowImage = view.findViewById<View>(R.id.arrowView) as ImageView
        val model = topCells[groupPosition].models?.get(childPosition)
        var modelChecked = false
        val selectedModel = MyMotApplication.searchManager.model
        if (selectedModel != null) {
            if (selectedModel.id == model?.id ?: 0) {
                modelChecked = true
            }
        }
        if (modelChecked) {
            arrowImage.setImageResource(R.drawable.ic_checked)
            arrowImage.visibility = View.VISIBLE
        } else {
            arrowImage.visibility = View.GONE
        }
        Picasso.get().load(Const.DOMAIN + model?.preview_picture)
            .placeholder(R.drawable.ic_placeholder).into(imageView)
        modelTitle.text = model?.name
        years.text = model?.years
        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}