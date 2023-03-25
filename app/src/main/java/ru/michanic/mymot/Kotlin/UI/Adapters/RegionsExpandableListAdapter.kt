package ru.michanic.mymot.Kotlin.UI.Adapters

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import ru.michanic.mymot.Kotlin.Enums.CellAccessoryType
import ru.michanic.mymot.Kotlin.Models.Location
import ru.michanic.mymot.Kotlin.MyMotApplication
import ru.michanic.mymot.R
import ru.michanic.mymot.Kotlin.UI.Cells.SimpleCell

class RegionsExpandableListAdapter(private val regions: List<Location>, var context: Context) :
    BaseExpandableListAdapter() {
    private val filterRegion = MyMotApplication.searchManager?.region
    override fun getGroupCount(): Int {
        return regions.size + 1
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return if (groupPosition == 0) {
            0
        } else {
            val region = regions[groupPosition - 1]
            val citiesCount = MyMotApplication.dataManager?.getRegionCitiesCount(region.id)
            if (citiesCount!! > 0) {
                citiesCount + 1
            } else {
                0
            }
        }
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
    ): View {
        val cell = View.inflate(context, R.layout.cell_simple, null)
        if (groupPosition == 0) {
            val state =
                if (filterRegion == null) CellAccessoryType.CHECKED else CellAccessoryType.HIDDEN
            SimpleCell.fillWithTitle(cell, "По всей России", state, 1)
        } else {
            val region = regions[groupPosition - 1]
            var cellAccessoryType = CellAccessoryType.BOTTOM
            if (isExpanded) {
                val citiesCount = MyMotApplication.dataManager?.getRegionCitiesCount(region.id)
                cellAccessoryType =
                    if (citiesCount == 0) CellAccessoryType.LOADING else CellAccessoryType.TOP
            }
            SimpleCell.fillWithTitle(cell, region.name ?: "", cellAccessoryType, 1)
        }
        return cell
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View,
        parent: ViewGroup
    ): View {
        val cell = View.inflate(context, R.layout.cell_simple, null)
        if (childPosition == 0) {
            var state = CellAccessoryType.HIDDEN
            if (filterRegion != null) {
                if (filterRegion.id == regions[groupPosition - 1].id) {
                    state = CellAccessoryType.CHECKED
                }
            }
            SimpleCell.fillWithTitle(cell, "Все города", state, 2)
        } else {
            val region = regions[groupPosition - 1]
            val cities = MyMotApplication.dataManager!!.getRegionCities(region.id)
            val city = cities[childPosition - 1]
            var state = CellAccessoryType.HIDDEN
            if (filterRegion != null) {
                Log.e("filterRegion", filterRegion.name)
                if (filterRegion.id == city.id) {
                    state = CellAccessoryType.CHECKED
                }
            }
            SimpleCell.fillWithTitle(cell, city.name ?: "", state, 2)
        }
        return cell
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}