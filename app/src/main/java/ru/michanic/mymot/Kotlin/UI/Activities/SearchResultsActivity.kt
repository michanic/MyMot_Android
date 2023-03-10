package ru.michanic.mymot.Kotlin.UI.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import ru.michanic.mymot.Interactors.SitesInteractor
import ru.michanic.mymot.Kotlin.Models.Advert
import ru.michanic.mymot.Kotlin.Models.SearchFilterConfig
import ru.michanic.mymot.MyMotApplication
import ru.michanic.mymot.Protocols.ClickListener
import ru.michanic.mymot.Protocols.FilterSettedInterface
import ru.michanic.mymot.Protocols.LoadingAdvertsInterface
import ru.michanic.mymot.R
import ru.michanic.mymot.Kotlin.UI.Adapters.AdvertsListAdapter

class SearchResultsActivity : UniversalActivity() {
    private var resultView: RecyclerView? = null
    private var glm: GridLayoutManager? = null
    private var searchAdapter: AdvertsListAdapter? = null
    private var progressBar: ProgressBar? = null
    private val sitesInteractor = SitesInteractor()
    private var filterConfig: SearchFilterConfig? = null
    private var currentPage = 1
    private var loading = false
    private var isLastPage = false
    private val loadedAdverts: MutableList<Advert> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)
        setNavigationTitle("Результаты поиска")
        resultView = findViewById<View>(R.id.resultsView) as RecyclerView
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        MyMotApplication.searchManager.filterClosedCallback =
            FilterSettedInterface { Log.e("filterClosedCallback", "") }
        MyMotApplication.searchManager.searchPressedCallback =
            FilterSettedInterface { Log.e("searchPressedCallback", "") }
        val advertPressed = ClickListener { section, row ->
            val adveryActivity = Intent(applicationContext, AdvertActivity::class.java)
            adveryActivity.putExtra("advertId", loadedAdverts[row].id)
            startActivity(adveryActivity)
        }
        glm = GridLayoutManager(this, 1)
        resultView?.layoutManager = glm
        searchAdapter = AdvertsListAdapter(this, loadedAdverts, advertPressed)
        resultView?.adapter = searchAdapter
        resultView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastvisibleitemposition = glm?.findLastVisibleItemPosition()
                if (lastvisibleitemposition == searchAdapter?.itemCount?.minus(1)) {
                    if (!loading && !isLastPage) {
                        loading = true
                        loadMore()
                    }
                }
            }
        })
        MyMotApplication.searchManager.filterClosedCallback =
            FilterSettedInterface { reloadResults() }
        reloadResults()
    }

    private fun reloadResults() {
        filterConfig = MyMotApplication.searchManager.filterConfig
        loadedAdverts.clear()
        searchAdapter?.notifyDataSetChanged()
        currentPage = 1
        progressBar?.visibility = View.VISIBLE
        loadMore()
    }

    private fun loadMore() {
        loading = true
        sitesInteractor.searchAdverts(currentPage, filterConfig, object : LoadingAdvertsInterface {
            override fun onLoaded(adverts: List<Advert>, loadMore: Boolean) {
                progressBar?.visibility = View.GONE
                Log.e("onLoaded", adverts.size.toString())
                loadedAdverts.addAll(adverts)
                searchAdapter?.notifyDataSetChanged()
                loading = false
                isLastPage = !loadMore
                currentPage++
            }

            override fun onFailed() {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.filter_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.filter_icon) {
            val filterActivity = Intent(applicationContext, FilterActivity::class.java)
            filterActivity.putExtra("goBackOnSearch", true)
            startActivity(filterActivity)
        }
        return super.onOptionsItemSelected(item)
    }
}