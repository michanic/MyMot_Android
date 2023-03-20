package ru.michanic.mymot.Kotlin.UI.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ExpandableListView
import ru.michanic.mymot.Kotlin.Interactors.ApiInteractor
import ru.michanic.mymot.Kotlin.MyMotApplication
import ru.michanic.mymot.Kotlin.Protocols.LoadingInterface
import ru.michanic.mymot.R
import ru.michanic.mymot.Kotlin.UI.Adapters.RegionsExpandableListAdapter
import ru.michanic.mymot.Kotlin.Utils.DataManager

class FilterRegionsActivity : UniversalActivity() {
    private var regionsExpandableListAdapter: RegionsExpandableListAdapter? = null
    private val apiInteractor = ApiInteractor()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_regions)
        setNavigationTitle("Регион")
        var expandGroupPosition = 0
        var expandedRegionId = 0
        val filterRegion = MyMotApplication.searchManager?.region
        if (filterRegion != null) {
            val cityRegion = MyMotApplication.dataManager?.getRegionById(filterRegion.regionId)
            expandedRegionId = cityRegion?.id ?: filterRegion.id
        }
        val regions = DataManager().regions
        if (expandedRegionId > 0) {
            var row = 1
            for (region in regions) {
                val citiesCount = MyMotApplication.dataManager?.getRegionCitiesCount(region.id)
                if (expandedRegionId == region.id) {
                    expandGroupPosition = row
                }
                row++
            }
        }
        regionsExpandableListAdapter = RegionsExpandableListAdapter(regions, this)
        val expandableListView = findViewById<View>(R.id.expandable_list_view) as ExpandableListView
        expandableListView.setAdapter(regionsExpandableListAdapter)
        expandableListView.setOnGroupClickListener { parent, v, groupPosition, id ->
            if (groupPosition == 0) {
                MyMotApplication.searchManager?.region = null
                onBackPressed()
            } else {
                val region = regions[groupPosition - 1]
                val citiesCount = MyMotApplication.dataManager?.getRegionCitiesCount(region.id)
                if (citiesCount == 0) {
                    apiInteractor.loadRegionCities(region, object : LoadingInterface {
                        override fun onLoaded() {
                            Log.e("expandGroup", groupPosition.toString())
                            expandableListView.collapseGroup(groupPosition)
                            expandableListView.expandGroup(groupPosition)
                        }

                        override fun onFailed() {}
                    })
                }
            }
            false
        }
        expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            if (childPosition == 0) {
                val region = regions[groupPosition - 1]
                MyMotApplication.searchManager?.region = region
            } else {
                val region = regions[groupPosition - 1]
                val city =
                    MyMotApplication.dataManager?.getRegionCities(region.id)?.get(childPosition - 1)
                MyMotApplication.searchManager?.region = city
            }
            onBackPressed()
            false
        }
        if (expandGroupPosition > 0) {
            expandableListView.expandGroup(expandGroupPosition)
            expandableListView.setSelectedGroup(expandGroupPosition)
        }
    }
}