package ru.michanic.mymot.Kotlin.UI.Activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.SearchView
import de.halfbit.pinnedsection.PinnedSectionListView
import ru.michanic.mymot.Kotlin.Models.SectionModelItem
import ru.michanic.mymot.Kotlin.MyMotApplication
import ru.michanic.mymot.R
import ru.michanic.mymot.Kotlin.UI.Adapters.SectionItemsListAdapter
import ru.michanic.mymot.Kotlin.UI.Frames.Catalog.CatalogHomeFragment
import ru.michanic.mymot.Kotlin.UI.Frames.Favourites.FavouritesHomeFragment
import ru.michanic.mymot.Kotlin.UI.Frames.Info.InfoHomeFragment
import ru.michanic.mymot.Kotlin.UI.Frames.Search.SearchHomeFragment
import ru.michanic.mymot.Kotlin.Utils.DataManager

class MainActivity : UniversalActivity() {

    private lateinit var navView: BottomNavigationView
    private var searchIcon: MenuItem? = null
    private var filterIcon: MenuItem? = null
    private var searchResultsView: PinnedSectionListView? = null
    private var searchResultsAdapter: SectionItemsListAdapter? = null
    private val dataManager = DataManager()
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var fragment: Fragment? = null
            setNavigationTitle(item.title.toString())
            when (item.itemId) {
                R.id.navigation_catalog -> {
                    fragment = CatalogHomeFragment()
                    showSearchIcon(false)
                    showFilterIcon(false)
                }
                /*R.id.navigation_search -> {
                    fragment = SearchHomeFragment()
                    showSearchIcon(true)
                    showFilterIcon(true)
                }*/
                R.id.navigation_favourites -> {
                    fragment = FavouritesHomeFragment()
                    showSearchIcon(false)
                    showFilterIcon(false)
                }
                R.id.navigation_info -> {
                    fragment = InfoHomeFragment()
                    showSearchIcon(false)
                    showFilterIcon(false)
                }
            }
            loadFragment(fragment)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        rootActivity = true
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(CatalogHomeFragment())
        setNavigationTitle(resources.getString(R.string.title_catalog))
        navView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        searchResultsView = findViewById<View>(R.id.listView) as PinnedSectionListView
        searchResultsView?.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.search_filter_menu, menu)
        val searchIcon = menu.findItem(R.id.search_icon)
        filterIcon = menu.findItem(R.id.filter_icon)
        showSearchIcon(false)
        showFilterIcon(false)
        val searchView = searchIcon.getActionView() as SearchView
        searchView.queryHint = "Модель или объем"
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.e("newText", newText)
                showSearchResults(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.filter_icon) {
            val filterActivity = Intent(applicationContext, FilterActivity::class.java)
            startActivity(filterActivity)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSearchIcon(show: Boolean) {
        if (searchIcon != null) {
            searchIcon?.isVisible = show
        }
    }

    private fun showFilterIcon(show: Boolean) {
        if (filterIcon != null) {
            filterIcon?.isVisible = show
        }
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            return true
        }
        return false
    }

    private fun showSearchResults(searchText: String) {
        if (searchText.length < 1) {
            searchResultsView?.visibility = View.GONE
        } else {
            val models = dataManager.searchModelsByName(searchText)
            if (models.size > 0) {
                val items: MutableList<SectionModelItem?> = ArrayList()
                for (model in models) {
                    items.add(SectionModelItem(model))
                }
                if (searchResultsAdapter != null) {
                    searchResultsAdapter?.setItems(items)
                    searchResultsAdapter?.notifyDataSetChanged()
                } else {
                    searchResultsAdapter = SectionItemsListAdapter(items)
                    searchResultsView?.adapter = searchResultsAdapter
                }
                searchResultsView?.visibility = View.VISIBLE
                searchResultsView?.onItemClickListener =
                    OnItemClickListener { parent, view, position, id ->
                        val model = items[position]?.model
                        /*MyMotApplication.searchManager?.model = model
                        val searchResultsActivity =
                            Intent(applicationContext, SearchResultsActivity::class.java)
                        startActivity(searchResultsActivity)*/
                        // раньше вызывался поиск объявлений по выбранной модели
                        val catalogModelActivity = Intent(applicationContext, CatalogModelActivity::class.java)
                        catalogModelActivity.putExtra("modelId", model?.id ?: 0)
                        startActivity(catalogModelActivity)
                    }
            } else {
                searchResultsView?.visibility = View.GONE
            }
        }
    }
}
