package ru.michanic.mymot.Kotlin.UI.Activities

import android.os.Bundle
import android.view.View
import android.widget.ExpandableListView
import ru.michanic.mymot.Models.FilterModelItem
import ru.michanic.mymot.MyMotApplication
import ru.michanic.mymot.R
import ru.michanic.mymot.Kotlin.UI.Adapters.ModelsExpandableListAdapter
import ru.michanic.mymot.Utils.DataManager

class FilterModelsActivity : UniversalActivity() {
    private var modelsExpandableListAdapter: ModelsExpandableListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_models)
        setNavigationTitle("Модель")
        var expandGroupPosition = 0
        val topCells: MutableList<FilterModelItem> = ArrayList()
        val allChecked =
            MyMotApplication.searchManager.manufacturer == null && MyMotApplication.searchManager.model == null
        topCells.add(FilterModelItem(allChecked))
        val dataManager = DataManager()
        val categories = dataManager.getCategories(true)
        var expandedManufacturerId = 0
        var expandedCategoryId = 0
        val filterModel = MyMotApplication.searchManager.model
        if (filterModel != null) {
            expandedManufacturerId = filterModel.manufacturer.id
            expandedCategoryId = filterModel.category.id
        }
        for (manufacturer in dataManager.getManufacturers(true)) {
            topCells.add(FilterModelItem(manufacturer.name))
            var manufacturerChecked = false
            val selectedManufacturer = MyMotApplication.searchManager.manufacturer
            if (selectedManufacturer != null) {
                if (selectedManufacturer.id == manufacturer.id) {
                    manufacturerChecked = true
                }
            }
            topCells.add(FilterModelItem(manufacturer, manufacturerChecked))
            for (category in categories) {
                val models = category.getManufacturerModels(manufacturer.id)
                if (models.size > 0) {
                    if (expandedCategoryId == category.id && expandedManufacturerId == manufacturer.id) {
                        expandGroupPosition = topCells.size
                    }
                    topCells.add(FilterModelItem(category, models))
                }
            }
        }
        modelsExpandableListAdapter = ModelsExpandableListAdapter(this, topCells)
        val expandableListView = findViewById<View>(R.id.expandable_list_view) as ExpandableListView
        expandableListView.setAdapter(modelsExpandableListAdapter)
        expandableListView.setOnGroupClickListener { parent, v, groupPosition, id ->
            if (groupPosition == 0) {
                MyMotApplication.searchManager.manufacturer = null
                MyMotApplication.searchManager.model = null
                onBackPressed()
            } else {
                val manufacturer = topCells[groupPosition].manufacturer
                if (manufacturer != null) {
                    MyMotApplication.searchManager.manufacturer = manufacturer
                    onBackPressed()
                }
            }
            false
        }
        expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            val model = topCells[groupPosition].models[childPosition]
            MyMotApplication.searchManager.model = model
            onBackPressed()
            false
        }
        if (expandGroupPosition > 0) {
            expandableListView.expandGroup(expandGroupPosition)
            expandableListView.setSelectedGroup(expandGroupPosition)
        }
    }
}