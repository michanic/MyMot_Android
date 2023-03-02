package ru.michanic.mymot.Kotlin.UI.Activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import de.halfbit.pinnedsection.PinnedSectionListView
import ru.michanic.mymot.Extensions.Font
import ru.michanic.mymot.Models.SectionModelItem
import ru.michanic.mymot.MyMotApplication
import ru.michanic.mymot.Protocols.FilterSettedInterface
import ru.michanic.mymot.R
import ru.michanic.mymot.Kotlin.UI.Adapters.SectionItemsListAdapter

class FilterActivity : UniversalActivity() {
    private var goBackOnSearch = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        setNavigationTitle("Фильтр")
        goBackOnSearch = intent.getBooleanExtra("goBackOnSearch", false)
        val searchButton = findViewById<View>(R.id.searchButton) as Button
        searchButton.typeface = Font.progress
        searchButton.setOnClickListener {
            if (goBackOnSearch) {
                MyMotApplication.searchManager.backPressed()
                finish()
            } else {
                val searchResultsActivity =
                    Intent(applicationContext, SearchResultsActivity::class.java)
                startActivity(searchResultsActivity)
            }
        }
        createCells()
        MyMotApplication.searchManager.filterUpdated = FilterSettedInterface { createCells() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        MyMotApplication.searchManager.backPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> MyMotApplication.searchManager.backPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createCells() {
        val items: MutableList<SectionModelItem?> = ArrayList()
        items.add(SectionModelItem("Регион поиска"))
        items.add(SectionModelItem(MyMotApplication.searchManager.regionTitle, null))
        items.add(SectionModelItem("Модель"))
        items.add(SectionModelItem(MyMotApplication.searchManager.modelTitle, null))
        items.add(SectionModelItem("Цена"))
        var priceFromString = ""
        val priceFromInt = MyMotApplication.searchManager.priceFrom
        if (priceFromInt != 0) {
            priceFromString = priceFromInt.toString()
        }
        items.add(SectionModelItem(SectionModelItem.PRICE_FROM_NAME, priceFromString))
        var priceForString = ""
        val priceForInt = MyMotApplication.searchManager.priceFor
        if (priceForInt != 0) {
            priceForString = priceForInt.toString()
        }
        items.add(SectionModelItem(SectionModelItem.PRICE_FOR_NAME, priceForString))
        val listView = findViewById<View>(R.id.listView) as PinnedSectionListView
        val sectionItemsListAdapter = SectionItemsListAdapter(items)
        listView.adapter = sectionItemsListAdapter
        listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            when (position) {
                1 -> {
                    val regionsActivity =
                        Intent(applicationContext, FilterRegionsActivity::class.java)
                    startActivity(regionsActivity)
                }
                3 -> {
                    val modelsActivity =
                        Intent(applicationContext, FilterModelsActivity::class.java)
                    startActivity(modelsActivity)
                }
            }
        }
    }
}