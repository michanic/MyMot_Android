package ru.michanic.mymot.Kotlin.UI.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import de.halfbit.pinnedsection.PinnedSectionListView
import ru.michanic.mymot.Models.SectionModelItem
import ru.michanic.mymot.R
import ru.michanic.mymot.Kotlin.UI.Adapters.SectionItemsListAdapter
import ru.michanic.mymot.Utils.DataManager
import java.util.*

class CatalogByManufacturerActivity : UniversalActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog_list)
        val dataManager = DataManager()
        val intent = intent
        val manufacturerId = intent.getIntExtra("manufacturerId", 0)
        val manufacturer = dataManager.getManufacturerById(manufacturerId)
        setNavigationTitle(manufacturer.name)
        val items: MutableList<SectionModelItem?> = ArrayList()
        for (category in dataManager.getCategories(true)) {
            val models = dataManager.getManufacturerModels(manufacturer, category)
            if (models.size > 0) {
                items.add(SectionModelItem(category.name.uppercase(Locale.getDefault())))
                for (model in models) {
                    items.add(SectionModelItem(model))
                }
            }
        }
        val listView = findViewById<View>(R.id.listView) as PinnedSectionListView
        val sectionItemsListAdapter = SectionItemsListAdapter(items)
        listView.adapter = sectionItemsListAdapter
        listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val model = items[position]?.model
            if (model != null) {
                val catalogModelActivity =
                    Intent(applicationContext, CatalogModelActivity::class.java)
                catalogModelActivity.putExtra("modelId", model.id)
                startActivity(catalogModelActivity)
            }
        }
    }
}